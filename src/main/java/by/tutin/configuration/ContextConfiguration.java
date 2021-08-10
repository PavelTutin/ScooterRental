package by.tutin.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan("by.tutin")
@EnableTransactionManagement
public class ContextConfiguration {
}
