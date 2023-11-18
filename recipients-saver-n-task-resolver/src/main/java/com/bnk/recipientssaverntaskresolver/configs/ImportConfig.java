package com.bnk.recipientssaverntaskresolver.configs;

import com.bnk.miscellaneous.EnableStore;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;


@Import({
        EnableStore.class
})
@Configuration
public class ImportConfig {
}
