package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

@Component
public class CustomStepListener implements StepExecutionListener {
	
	private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);
				
	@Override
	public void beforeStep(StepExecution stepExecution) {
		log.info("StepExecutionListener - beforeStep, step name: " + stepExecution.getStepName());
		PersonItemProcessor itemProcessor = new PersonItemProcessor();
		
		ExecutionContext stepContext = stepExecution.getExecutionContext();
        stepContext.put("dataOfPreviousStep", itemProcessor.getDataOfPreviousStep());
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("StepExecutionListener - afterStep, step name: " + stepExecution.getStepName());
		PersonItemProcessor itemProcessor = new PersonItemProcessor();
		
        JobExecution jobExecution = stepExecution.getJobExecution();
        ExecutionContext jobContext = jobExecution.getExecutionContext();
        itemProcessor.setDataOfPreviousStep(jobContext.get("dataOfPreviousStep"));
		
		return null;
	}

}