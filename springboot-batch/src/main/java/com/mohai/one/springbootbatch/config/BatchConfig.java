package com.mohai.one.springbootbatch.config;

import com.mohai.one.springbootbatch.dao.UserRepository;
import com.mohai.one.springbootbatch.domain.UserDTO;
import com.mohai.one.springbootbatch.listener.JobRunListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import java.io.FileNotFoundException;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/8/10 23:46
 */
@Configuration
//实现多个job，需要配置modular = true
@EnableBatchProcessing(modular = true)
public class BatchConfig {

    // 配置一个job
    @Bean
    public Job importUserJob(JobBuilderFactory jobBuilderFactory, Step step) {
        // 为job起名为 importUserJob
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
               // .flow(step)
              //  .end()
                .start(step)
                .listener(listener())
                .build();
    }

    @Bean
    public Job importUserJob2(JobBuilderFactory jobBuilderFactory, Step step) {
        // 为job起名为 importUserJob
        return jobBuilderFactory.get("importUserJob2")
                .incrementer(new RunIdIncrementer())
                // .flow(step)
                //  .end()
                .start(step)
                .listener(listener())
                .build();
    }

    // 配置一个Step
    @Bean
    public Step importUserStep(
            StepBuilderFactory stepBuilderFactory,
            ItemReader<UserDTO> reader,
            ItemProcessor<UserDTO,UserDTO> processor,
            ItemWriter<UserDTO> writer) {

        return stepBuilderFactory.get("importUserStep")
                // 批处理每次提交10条数据
                .<UserDTO, UserDTO>chunk(10)
                // 给step绑定 reader
                .reader(reader)
                // 给step绑定 processor
                .processor(processor)
                // 给step绑定 writer
                .writer(writer)
                .faultTolerant()
                // 设定一个我们允许的这个step可以跳过的异常数量，
                // 这里设定为3，表示当这个step运行时，只要出现的异常数目不超过3条，整个step都不会失败。
                // 注意，若不设定skipLimit，其默认值是0
                .skipLimit(3)
                // 指定可以跳过的异常，因为有些异常的出现，我们是可以忽略的
                .skip(Exception.class)
                // 指定不想跳的异常，因此这种异常出现一次时，计数器就会加一，直到达到上限
                .noSkip(FileNotFoundException.class)
                .build();
    }

    // 从文件中读取数据
    @Bean
    public ItemReader<UserDTO> reader() {
        // FlatFileItemReader是一个用来加载文件的itemReader
        FlatFileItemReader<UserDTO> reader = new FlatFileItemReader<>();
        // 跳过第一行的标题
        reader.setLinesToSkip(1);
        // 设置csv的位置
        reader.setResource(new ClassPathResource("user.csv"));
        // 设置每一行的数据信息
        reader.setLineMapper(new DefaultLineMapper<UserDTO>(){{
            setLineTokenizer(new DelimitedLineTokenizer(){{
                // 配置属性解析
                setNames(new String[]{"id","name","age"});
                // 配置列于列之间的间隔符,会通过间隔符对每一行进行切分
                setDelimiter(",");
            }});

            // 设置要映射的实体类属性
            setFieldSetMapper(new BeanWrapperFieldSetMapper<UserDTO>(){{
                setTargetType(UserDTO.class);
            }});
        }});
        return reader;
    }

    // 用来处理数据
    @Bean
    public ItemProcessor<UserDTO,UserDTO> processor(){
        // 使用我们自定义的ItemProcessor的实现UserItemProcessor
        UserItemProcessor processor = new UserItemProcessor();
        // 为processor指定校验器为UserBeanValidator
        processor.setValidator(validator());
        return processor;
    }

    // 用来输出数据
//    @Bean
//    public ItemWriter<UserDTO> writer(@Qualifier("dataSource") DataSource dataSource) {
//        // 通过JDBC写入到数据库中
//        JdbcBatchItemWriter writer = new JdbcBatchItemWriter();
//        writer.setDataSource(dataSource);
//        // setItemSqlParameterSourceProvider 表示将实体类中的属性和占位符一一映射
//        writer.setItemSqlParameterSourceProvider(
//                new BeanPropertyItemSqlParameterSourceProvider<>());
//
//
//        // 设置要执行批处理的SQL语句。其中占位符的写法是":属性名"
//        writer.setSql("insert into user(id, name, age, status, create_time) " +
//                "values(:id, :name, :age, :status, :createTime)");
//        return writer;
//    }

    // 向数据库写数据
    @Bean
    public ItemWriter<UserDTO> writer(@Qualifier("userRepository") UserRepository userRepository) {
        // 通过JPA写入到数据库中
        RepositoryItemWriterBuilder repositoryItemWriterBuilder = new RepositoryItemWriterBuilder();
        repositoryItemWriterBuilder.repository(userRepository);
        repositoryItemWriterBuilder.methodName("save");
        RepositoryItemWriter writer = repositoryItemWriterBuilder.build();
        return writer;
    }

    @Bean
    public Validator<UserDTO> validator(){
        return new UserBeanValidator<>();
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobRunListener();
    }

}