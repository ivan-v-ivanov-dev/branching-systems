package com.personal.vacation.repository;

import com.personal.vacation.model.Vacation;
import com.personal.vacation.repository.contract.VacationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    @Override
    public void create(Vacation vacation) {
        mongoTemplate.save(vacation, "vacations");
    }

    @Override
    public void updateSickLeave(String vacationId, String file) {
        Query query = new Query(Criteria.where("id").is(vacationId));
        Update update = new Update().set("list", file);
        mongoTemplate.updateFirst(query, update, Vacation.class, "vacations");
    }

    @Override
    public void update(String id, LocalDate startDate, LocalDate endDate, boolean halfDay) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        if (startDate != null) {
            update.set("startDate", startDate);
        }
        if (endDate != null) {
            update.set("endDate", endDate);
        }
        update.set("halfDay", halfDay);
        mongoTemplate.updateFirst(query, update, Vacation.class, "vacations");
    }

    @Override
    public Vacation findById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, Vacation.class, "vacations");
    }

    @Override
    public long delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id).and("approved").is(false));
        return mongoTemplate.remove(query, Vacation.class, "vacations").getDeletedCount();
    }

    @Override
    public void approve(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("approved", true);
        mongoTemplate.updateFirst(query, update, Vacation.class, "vacations");
    }
}
