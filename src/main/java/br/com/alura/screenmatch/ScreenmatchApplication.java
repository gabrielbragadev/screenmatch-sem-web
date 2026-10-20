package br.com.alura.screenmatch;

import br.com.alura.screenmatch.models.DadosEpisodio;
import br.com.alura.screenmatch.models.DadosSerie;
import br.com.alura.screenmatch.models.DadosTemporada;
import br.com.alura.screenmatch.services.ConsumoAPI;
import br.com.alura.screenmatch.services.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ConsumoAPI consumoAPI = new ConsumoAPI();
        var jsonDadosSerie = consumoAPI.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season=1&apikey=c6589c7e");
        var jsonDadosEpisodio = consumoAPI.obterDados("https://www.omdbapi.com/?t=Gilmore+Girls&season=1&episode=1&apikey=c6589c7e");
        ConverteDados conversor = new ConverteDados();
        DadosSerie dadosSerie = conversor.obterDados(jsonDadosSerie, DadosSerie.class);
		DadosEpisodio dadosEpisodio = conversor.obterDados(jsonDadosEpisodio, DadosEpisodio.class);

		System.out.println("Dados Episodio: ");
		System.out.println(dadosEpisodio);

		List<DadosTemporada> listaTemporadas = new ArrayList<>();

		for (int i = 1; i <= dadosSerie.totalTemporadas() ; i++) {

			String jsonDadosTemporada = consumoAPI.obterDados("https://www.omdbapi.com/?t=Gilmore+Girls&season=" + i + "&apikey=c6589c7e");
			DadosTemporada dadosTemporada = conversor.obterDados(jsonDadosTemporada, DadosTemporada.class);
			listaTemporadas.add(dadosTemporada);
		}

		listaTemporadas.forEach(System.out::println);


    }
}
