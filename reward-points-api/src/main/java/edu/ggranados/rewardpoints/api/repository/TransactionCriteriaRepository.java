package edu.ggranados.rewardpoints.api.repository;

import edu.ggranados.rewardpoints.api.entity.Transaction;
import edu.ggranados.rewardpoints.api.entity.TransactionPage;
import edu.ggranados.rewardpoints.api.entity.TransactionSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class TransactionCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    @Autowired
    public TransactionCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Transaction> findAllWithFilters(TransactionPage page,
                                                TransactionSearchCriteria searchCriteria){
        CriteriaQuery<Transaction> criteriaQuery = criteriaBuilder.createQuery(Transaction.class);
        Root<Transaction> root = criteriaQuery.from(Transaction.class);
        Predicate predicate = getPredicate(searchCriteria,root);

        criteriaQuery.where(predicate);
        setOrder(page, criteriaQuery, root);

        TypedQuery<Transaction> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(page.getPageNumber() * page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());

        Pageable pageable = getPageable(page);

        long transactionCount = getTransactionCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, transactionCount);
    }

    private Predicate getPredicate(TransactionSearchCriteria searchCriteria, Root<Transaction> root) {
        List<Predicate> predicates = new ArrayList<>();

        if(Objects.nonNull(searchCriteria.getClientId())){
            predicates.add(
                    criteriaBuilder.like(root.get("clientId"),
                    "%" + searchCriteria.getClientId() + "%")
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(TransactionPage page,
                          CriteriaQuery<Transaction> criteriaQuery,
                          Root<Transaction> root) {
        if(page.getSorDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(page.getSortBy())));
        }else {
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(page.getSortBy())));
        }
    }

    private Pageable getPageable(TransactionPage page) {
        Sort sort = Sort.by(page.getSorDirection(), page.getSortBy());
        return PageRequest.of(page.getPageNumber(), page.getPageSize(), sort);
    }

    private long getTransactionCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Transaction> countRoot = countQuery.from(Transaction.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);

        return entityManager.createQuery(countQuery).getSingleResult();
    }
}