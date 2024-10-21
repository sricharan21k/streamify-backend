package com.app.streamify;

import com.app.streamify.enumeration.UserListType;
import com.app.streamify.model.*;
import com.app.streamify.repository.*;
import com.app.streamify.service.MusicService;
import com.app.streamify.service.PodcastService;
import com.app.streamify.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest
class StreamifyApplicationTests {


	@Autowired private SongRepository songRepository;
	@Autowired private AlbumRepository albumRepository;
	@Autowired private ArtistRepository artistRepository;
	@Autowired private MovieRepository movieRepository;
	@Autowired private ShowRepository showRepository;
	@Autowired private PodcastRepository podcastRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private MusicService musicService;
	@Autowired private PodcastService podcastService;
	@Autowired private UserService userService;
	@Autowired private BCryptPasswordEncoder encoder;

//	@BeforeAll
//	public static void setUp() {
//		System.setProperty("spring.datasource.url", "jdbc:postgresql://ep-dawn-mountain-a5x2ubow.us-east-2.aws.neon.tech/streamify-db");
//		System.setProperty("spring.datasource.username", "streamify-db_owner");
//		System.setProperty("spring.datasource.password", "XOVFREa9qx0h");
//	}


	@Test
	public void addMusicData(){
		// The Weeknd
		Artist artist = Artist.builder().name("The Weeknd").image("https://i.postimg.cc/pLVjYWcc/weeknd.jpg").build();
		artistRepository.save(artist);

		Album album1 = Album.builder().title("Starboy").genre("R&B").year(2016).isSingle(false).artist(artist)
				.albumCover("https://i.postimg.cc/pXSzPHvF/The-Weeknd-Starboy.png")
				.trackList(new ArrayList<>()).build();

		Album album2 = Album.builder().title("Beauty Behind the Madness").genre("Alternative R&B").year(2015).isSingle(false)
				.albumCover("https://i.postimg.cc/Hs9RN61R/The-Weeknd-Beauty-Behind-the-Madness.png")
				.artist(artist).trackList(new ArrayList<>()).build();

		Album album3 = Album.builder().title("After Hours").genre("R&B").year(2020).isSingle(false).artist(artist)
				.albumCover("https://i.postimg.cc/qvdBsbXF/The-Weeknd-After-Hours.png")
				.trackList(new ArrayList<>()).build();

		Album album4 = Album.builder().title("Dawn FM").genre("Synth-pop").year(2022).isSingle(false).artist(artist)
				.albumCover("https://i.postimg.cc/k4D7Jkq2/The-Weeknd-Dawn-FM.png")
				.trackList(new ArrayList<>()).build();


		Song song1 = Song.builder().title("Starboy").genre("R&B").year(2016).artist("The Weeknd").featuring("Daft Punk").album(album1).build();
		Song song2 = Song.builder().title("False Alarm").genre("Dance-punk").year(2016).artist("The Weeknd").album(album1).build();
		Song song3 = Song.builder().title("I Feel It Coming").genre("Disco-pop").year(2016).artist("The Weeknd").album(album1).build();
		Song song4 = Song.builder().title("Party Monster").genre("Trap").year(2016).artist("The Weeknd").album(album1).build();
		Song song5 = Song.builder().title("Reminder").genre("R&B").year(2017).artist("The Weeknd").album(album1).build();
		Song song6 = Song.builder().title("Rockin'").genre("Electro-dance").year(2017).artist("The Weeknd").album(album1).build();
		Song song7 = Song.builder().title("Die For You").genre("R&B").year(2017).artist("The Weeknd").album(album1).build();
		Song song8 = Song.builder().title("Secrets").genre("Disco-house").year(2017).artist("The Weeknd").album(album1).build();

		Song song9 = Song.builder().title("Often").genre("Alternative R&B").year(2014).artist("The Weeknd").album(album2).build();
		Song song10 = Song.builder().title("The Hills").genre("Alternative R&B").year(2015).artist("The Weeknd").album(album2).build();
		Song song11 = Song.builder().title("Can't Feel My Face").genre("Pop").year(2015).artist("The Weeknd").album(album2).build();
		Song song12 = Song.builder().title("In the Night").genre("Disco").year(2015).artist("The Weeknd").album(album2).build();
		Song song13 = Song.builder().title("Acquainted").genre("Alternative R&B").year(2015).artist("The Weeknd").album(album2).build();

		Song song14 = Song.builder().title("Heartless").genre("R&B").year(2019).artist("The Weeknd").album(album3).build();
		Song song15 = Song.builder().title("Blinding Lights").genre("Synth-pop").year(2019).artist("The Weeknd").album(album3).build();
		Song song16 = Song.builder().title("In Your Eyes").genre("Synth-pop").year(2020).artist("The Weeknd").album(album3).build();
		Song song17 = Song.builder().title("Save YOur Tears").genre("Synth-pop").year(2020).artist("The Weeknd").album(album3).build();

		Song song18 = Song.builder().title("Take My Breath").genre("Dance-pop").year(2021).artist("The Weeknd").album(album4).build();
		Song song19 = Song.builder().title("Sacrifice").genre("Electro").year(2022).artist("The Weeknd").album(album4).build();
		Song song20 = Song.builder().title("Out of Time").genre("R&B").year(2022).artist("The Weeknd").album(album4).build();
		Song song21 = Song.builder().title("Less than Zero").genre("New wave").year(2022).artist("The Weeknd").album(album4).build();

		album1.getTrackList().addAll((List.of(song1, song2, song3, song4, song5, song6, song7, song8)));
		album2.getTrackList().addAll(List.of(song9, song10, song11, song12, song13));
		album3.getTrackList().addAll(List.of(song14, song15, song16, song17));
		album4.getTrackList().addAll(List.of(song18, song19, song20, song21));

		albumRepository.saveAll(List.of(album1, album2, album3, album4));
	}

	@Test
	public void addMovieData(){

		// Sci-fi
		Movie m1 = Movie.builder().title("Avatar").genre("Sci-fi").director("James Cameron").producer("Jon Landau").year(2009)
				.cast("Sam Worthington,Zoe Saldana,Stephen Lang,Michelle Rodriguez,Sigourney Weaver")
				.crew("Writer:James Cameron,Screenplay:James Cameron,Music:James Horner,Cinematography:Mauro Fiore,Editor:Stephen Rivkin")
				.production("20th Century Fox").distributor("20th Century Fox")
				.runtime(162).language("English").isFranchise(true).franchiseName("Avatar")
				.poster("https://i.postimg.cc/zGRbgV8y/Avatar-2009-film-poster.jpg")
				.landscapePoster("https://i.postimg.cc/d0ptcZCK/avatar-landscape.jpg").rating(7.9).build();

		Movie m2 = Movie.builder().title("Avatar: The Way of Water").genre("Sci-fi").director("James Cameron").producer("Jon Landau").year(2022)
				.cast("Sam Worthington,Zoe Saldana,Sigourney Weaver,Stephen Lang,Kate Winslet")
				.crew("Writer:James Cameron,Screenplay:James Cameron,Music:Simon Franglen,Cinematography:Russel Carpenter,Editor:Stephen Rivkin")
				.production("TSG Entertainment, Lightstorm Entertainment").distributor("20th Century Studios")
				.runtime(192).language("English").isFranchise(true).franchiseName("Avatar")
				.poster("https://i.postimg.cc/RVZb4z9s/Avatar-The-Way-of-Water-poster.jpg").rating(7.5).build();

		Movie m3 = Movie.builder().title("Kalki 2898 AD").genre("Sci-fi").director("Nag Ashwin").producer("Aswini Dutt").year(2024)
				.cast("Prabhas,Amitabh Bachchan,Kamal Haasan,Deepika Padukone,Disha Patani")
				.crew("Writer:Nag Ashwin,Screenplay:Nag Ashwin,Music:Santhosh Narayan,Cinematography:Djordje Stojiljkovic,Editor:Kotagiri Venkateswara Rao")
				.production("Vyjayanthi Movies").distributor("Annapurna Studios")
				.runtime(181).language("Telugu").isFranchise(true).franchiseName("Kalki")
				.poster("https://i.postimg.cc/qqhhxRyD/Kalki-2898-AD.jpg").rating(7.7).build();

		Movie m4 = Movie.builder().title("Godzilla x Kong: The New Empire").genre("Sci-fi").director("Adam Wingard").producer("Thomas Tull, Jon Jashni").year(2024)
				.cast("Rebecca Hall,Brian Tyree Henry,Dan Stevens,Kaylee Hottle")
				.crew("Writer:Terry Rossio,Screenplay:Adam Wingard,Music:Tom Holkenberg,Cinematography:Ben Seresin,Editor:Josh Schaeffer")
				.production("Legendary Pictures").distributor("Warner Bros. Studios")
				.runtime(115).language("English").isFranchise(true).franchiseName("Monsterverse")
				.poster("https://i.postimg.cc/26wfyR6H/Godzilla-x-kong-the-new-empire-poster.jpg").rating(6.1).build();

		movieRepository.saveAll(List.of(m1, m2, m3, m4));

	}

	@Test
	public void addShowData(){
		//Monarch
		Show show1 = Show.builder().title("Monarch: Legacy of Monsters").genre("Sci-fi").seasons(1)
				.creator("Chris Black,Matte Fraction").composer("Leopold Ross")
				.starring("Anna Sawai,Wyatt Russel,Kurt Russel,Mari Yamamoto,Anders Holm,Ren Watabe,Kiersey Clemons")
				.language("English").initialRelease(2023).rating(7.6)
				.poster("https://i.postimg.cc/J7xtQ7L4/Monarch-Legacy-of-Monsters-poster.jpg")
				.otherGenres("Action,Adventure").episodeList(new ArrayList<>())
				.description("Set after the battle between Godzilla and the Titans, revealing that monsters are real, follows one family's journey to uncover its buried secrets and a legacy linking them to Monarch.").build();

		Episode e1 = Episode.builder().title("Aftermath").season(1).episode(1)
				.releaseDate(LocalDate.of(2023, 11, 17)).duration(49).showName(show1)
				.description("Cate travels to Japan to get answers about her father and uncovers a shocking secret. Keiko, Lee, and Billy pursue a theory about Titans.").build();
		Episode e2 = Episode.builder().title("Departure").season(1).episode(2)
				.releaseDate(LocalDate.of(2023, 11, 17)).duration(46).showName(show1)
				.description("Monarch closes in on Cate, Kentaro, and May as they try to track down a key individual. Keiko, Lee, and Billy stumble on a major find.").build();
		Episode e3 = Episode.builder().title("Secrets and Lies").season(1).episode(3)
				.releaseDate(LocalDate.of(2023, 11, 22)).duration(43).showName(show1)
				.description("Shaw leads the team on a dangerous mission to Alaska in search of Hiroshi. Keiko, Lee, and Billy present their findings to General Puckett.").build();
		Episode e4 = Episode.builder().title("Parallels and Interiors").season(1).episode(4)
				.releaseDate(LocalDate.of(2023, 12, 1)).duration(41).showName(show1)
				.description("The team is left stranded in the frozen tundra after a narrow escape. Kentaro reflects on his relationships.").build();
		Episode e5 = Episode.builder().title("The Way Out").season(1).episode(5)
				.releaseDate(LocalDate.of(2023, 12, 8)).duration(50).showName(show1)
				.description("Cate's painful memories of G-Day come flooding back as she treks through the ruins of San Francisco with Kentaro and May.").build();
		Episode e6 = Episode.builder().title("Terrifying Miracles").season(1).episode(6)
				.releaseDate(LocalDate.of(2023, 12, 15)).duration(49).showName(show1)
				.description("Shaw finds an unlikely ally within Monarch as the team has a harrowing run-in. Keiko and Lee grow closer while at a military ball.").build();
		Episode e7 = Episode.builder().title("Will the Real May Please Stand Up?").season(1).episode(7)
				.releaseDate(LocalDate.of(2023, 12, 22)).duration(47).showName(show1)
				.description("May finally faces her past. Duvall and Shaw follow traces of Godzilla and Hiroshi.").build();
		Episode e8 = Episode.builder().title("Birth Right").season(1).episode(8)
				.releaseDate(LocalDate.of(2023, 12, 29)).duration(42).showName(show1)
				.description("The team goes back to where things all began to confront Shaw. On the brink of collapse, Monarch takes drastic measures.").build();
		Episode e9 = Episode.builder().title("Axis Mundi").season(1).episode(9)
				.releaseDate(LocalDate.of(2024, 1, 5)).duration(44).showName(show1)
				.description("Shaw and May search for Cate and make a startling discovery. Kentaro struggles with his loss.").build();
		Episode e10 = Episode.builder().title("Beyond Logic").season(1).episode(10)
				.releaseDate(LocalDate.of(2024, 1, 12)).duration(44).showName(show1)
				.description("Season finale. The team struggles to find a way out of Axis Mundi. Kentaro and Tim make an unexpected alliance.").build();

		show1.getEpisodeList().addAll(List.of(e1,e2,e3,e4,e5,e6,e7,e8,e9,e10));

		showRepository.save(show1);


	}

	@Test
	public void addPodcastData(){
		Podcast p1 = Podcast.builder()
				.title("The Daily").topic("Long-form journalism").host("Micheal Barbaro,Sabrina Tavernise")
				.language("English").initialRelease(2017)
				.thumbnail("https://i.postimg.cc/13mKSfwf/The-Daily-logo.jpg").build();

		Podcast p2 = Podcast.builder()
				.title("The American Life").topic("Radio short stories and essays").host("Ira Glass")
				.language("English").initialRelease(1995)
				.thumbnail("https://i.postimg.cc/bJ7KVQGG/330px-Thisamericanlife-wbez.png").build();

		Podcast p3 = Podcast.builder()
				.title("Criminal").topic("True crime").host("Phoebe Judge")
				.language("English").initialRelease(2014)
				.thumbnail("https://i.postimg.cc/rsb3R5Ty/Criminal-Vox-Media-Podcast.png").build();

		Podcast p4 = Podcast.builder()
				.title("SmartLess").topic("Comedy").host("Will Arnett,Jason Bateman,Sean Hayes")
				.language("English").initialRelease(2020)
				.thumbnail("https://i.postimg.cc/wxhX2VcY/Smart-Less-cover.jpg").build();

		Podcast p5 = Podcast.builder()
				.title("Planet Money").topic("Economic,culture,business").host("Amanda Aronczyk,Erika Beras,Mary Childs,Nick Fountain")
				.language("English").initialRelease(2008)
				.thumbnail("https://i.postimg.cc/K8zQ0H5s/NPR-Planet-Money-cover-art-webp.png").build();

		Podcast p6 = Podcast.builder()
				.title("How I Built This").topic("Business").host("Guy Raz")
				.language("English").initialRelease(2016)
				.thumbnail("https://i.postimg.cc/V69166pT/NPR-How-I-Built-This-cover-art.jpg").build();

		podcastRepository.saveAll(List.of(p1, p2, p3, p4, p5, p6));

	}

	@Test
	public void testGetAlbum(){
		System.out.println(musicService.getAlbum(1L));
	}

	@Test
	public void testGetPodcastTopics(){
		System.out.println(podcastService.getAllTopics());
	}

	@Test
	public void testAddWatchHistoryData(){
		List<String> history = List.of(
                "1-movie", "2-movie", "3-movie","4-movie","1-show"
        );

		User user = userRepository.findByUsername("sricharan21k").orElseThrow();
		user.getWatchHistory().addAll(history);
		userRepository.save(user);
	}

	@Test
	public void testAddWatchlistData(){
		List<String> history = List.of(
				"1-movie", "2-movie", "3-movie","4-movie","1-show"
		);

		User user = userRepository.findByUsername("sricharan21k").orElseThrow();
		user.getWatchlist().addAll(history);
		userRepository.save(user);
	}

	@Test
	public void testAddRecentPlaysData(){
		List<String> history = List.of(
				"1-album", "2-album", "3-song","4-song","5-podcast", "1-podcast", "6-album"
		);

		User user = userRepository.findByUsername("sricharan21k").orElseThrow();
		user.getRecentPlays().addAll(history);
		userRepository.save(user);
	}

	@Test
	public void testAddPlayQueueData(){
		List<String> history = List.of(
				"1-album", "2-album", "3-song","4-song","5-podcast", "1-podcast", "6-album"
		);

		User user = userRepository.findByUsername("sricharan21k").orElseThrow();
		user.getPlayQueue().addAll(history);
		userRepository.save(user);
	}

	@Test
	public void testSearchLibrary(){
		System.out.println(movieRepository.searchLibrary("sam"));
	}

	@Test
	public void testGetLastPlayed(){
		System.out.println(userService.getLastPlayed("sricharan21k"));
	}

	@Test
	public void testGetUserList(){
		User user = userRepository.findByUsername("sricharan21k").orElseThrow();
//		System.out.println(userService.getUserlist("sricharan21k", UserListType.SEARCH));
		System.out.println(user.getSearchHistory());
	}

	@Test
	public void testAddAddToUserList(){
		System.out.println(userService.addItemToUserLibrary("sricharan21k", UserListType.WATCHLIST, "3-movie"));
	}

	@Test
	public void changePassword(){
		User user = userRepository.findByUsername("sricharan21k").get();
		user.setPassword(encoder.encode("qwerty123"));
		userRepository.save(user);
	}
}
