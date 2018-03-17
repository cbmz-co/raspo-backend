package it.cbmz.raspo.backend.controller;

import it.cbmz.raspo.backend.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;
import reactor.core.publisher.UnicastProcessor;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("hello", "ciao");
		return "home";
	}

	@Autowired
	private UnicastProcessor<Message> unicastProcessor;

	//invio messaggi a tutti i client connessi
	@PostMapping("/sendcommand")
	public void command(@RequestParam("command") String command) {
		unicastProcessor.onNext(Message.of(command, ""));
	}

	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setTemplateLoaderPath("classpath:/templates");
		return configurer;
	}

}
