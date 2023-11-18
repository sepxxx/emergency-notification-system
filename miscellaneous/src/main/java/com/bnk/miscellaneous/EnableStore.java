package com.bnk.miscellaneous;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

//@ComponentScan("ru.maycode.store.dao")
@EntityScan("com.bnk.miscellaneous.entities")
@EnableJpaRepositories("com.bnk.miscellaneous.repositories")
public class EnableStore {
}