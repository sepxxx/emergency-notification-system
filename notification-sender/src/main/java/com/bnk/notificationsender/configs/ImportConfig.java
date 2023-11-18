package com.bnk.notificationsender.configs;

import com.bnk.miscellaneous.EnableStore;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Import({
        EnableStore.class
})
@Configuration
public class ImportConfig {
}
