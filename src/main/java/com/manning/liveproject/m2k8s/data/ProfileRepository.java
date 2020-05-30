package com.manning.liveproject.m2k8s.data;

import com.manning.liveproject.m2k8s.data.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUsername(String username);
}

