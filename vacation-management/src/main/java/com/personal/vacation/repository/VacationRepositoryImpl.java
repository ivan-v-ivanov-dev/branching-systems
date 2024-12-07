package com.personal.vacation.repository;

import com.personal.vacation.model.Vacation;
import com.personal.vacation.repository.contract.VacationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class VacationRepositoryImpl implements VacationRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Vacation> findUserVacations(String applicant) {
        Query query = new Query();
        query.addCriteria(Criteria.where("applicant").is(applicant));
        query.with(Sort.by(Sort.Direction.ASC, "submittedOn"));
        return mongoTemplate.find(query, Vacation.class, "vacations");
    }
}
