package com.the.hugging.team.repositories;

import com.the.hugging.team.entities.Activity;

import java.util.List;
import java.util.Optional;

public class ActivityRepository implements ObjectRepository<Activity>{
    @Override
    public void save(Activity obj) {

    }

    @Override
    public void update(Activity obj) {

    }

    @Override
    public void delete(Activity obj) {

    }

    @Override
    public Optional<Activity> getById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Activity> getAll() {
        return null;
    }
}
