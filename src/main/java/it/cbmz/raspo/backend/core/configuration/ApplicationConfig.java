package it.cbmz.raspo.backend.core.configuration;

import it.cbmz.raspo.backend.core.command.client.ClientCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "it.cbmz.raspo.backend.core.repos")
public class ApplicationConfig {

	@Bean(name = "commandMap")
	public Map<String, ClientCommand> commandMap(List<ClientCommand> commands){

		Map<String, ClientCommand> _commands = commands.stream().collect(
			Collectors.toConcurrentMap(
				ClientCommand::commandName, Function.identity())
		);

		return new ConcurrentHashMap<String, ClientCommand>(_commands) {
			private ClientCommand _defaultValue = get("default");

			@Override
			public ClientCommand get(Object key) {

				ClientCommand command = super.get(key);

				if(command == null) {
					return _defaultValue;
				}

				return command;
			}
		};
	}
}
