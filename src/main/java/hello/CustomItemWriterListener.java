package hello;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

@Component
public  class CustomItemWriterListener implements ItemWriteListener<Person> {

	@Override
	public void beforeWrite(List<? extends Person> items) {
		System.out.println("ItemWriteListener - beforeWrite");
	}

	@Override
	public void afterWrite(List<? extends Person> items) {
		System.out.println("ItemWriteListener - afterWrite");
		PersonItemProcessor itemProcessor = new PersonItemProcessor();
		itemProcessor.setDataOfPreviousStep(items);
	}

	@Override
	public void onWriteError(Exception exception, List<? extends Person> items) {
		System.out.println("ItemWriteListener - onWriteError");
	}

}
