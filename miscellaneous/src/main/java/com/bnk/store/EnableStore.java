package com.bnk.store;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.bnk.store.entities")
@EnableJpaRepositories("com.bnk.store.repositories")
public class EnableStore {
}
