package io.javabrains.springbootstarter.topic;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {
	
	@RequestMapping("/topic")
	public List<Topic>getAllTopic() {
		return Arrays.asList(
                new Topic("name1","Spring1"),
                new Topic("name2","Spring2"),
                new Topic("name3","Spring3")
				);
	}
	/*public String getAllTopic() {
		return "all topics";
	}*/

}
