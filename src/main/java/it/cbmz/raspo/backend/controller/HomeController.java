package it.cbmz.raspo.backend.controller;

import it.cbmz.raspo.backend.message.Message;
import it.cbmz.raspo.backend.model.User;
import it.cbmz.raspo.backend.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;
import reactor.core.publisher.UnicastProcessor;

import java.util.logging.Logger;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("hello", "ciao");
		return "home";
	}

	@GetMapping("/addUser")
	public void addUser(@RequestParam("userName") String userName, @RequestParam("eMail") String eMail){
		User u = new User();
		u.setEMail(eMail);
		u.setUserName(userName);
		u = userRepo.save(u);
		_log.info("UUID: "+u.getUuid());

	}

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

	@Autowired
	private UnicastProcessor<Message> unicastProcessor;
	@Autowired
	private UserRepo userRepo;
	private final Logger _log =
			Logger.getLogger(HomeController.class.getName());
}
