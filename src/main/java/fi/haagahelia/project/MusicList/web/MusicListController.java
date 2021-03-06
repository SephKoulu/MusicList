package fi.haagahelia.project.MusicList.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.haagahelia.project.MusicList.domain.Song;
import fi.haagahelia.project.MusicList.domain.SongRepository;
import fi.haagahelia.project.MusicList.domain.Genre;
import fi.haagahelia.project.MusicList.domain.GenreRepository;






@Controller
public class MusicListController {
	
	@Autowired
	private SongRepository srepository; 
	
	@Autowired
	private GenreRepository grepository;
	
	  @RequestMapping(value="/login")
	    public String login() {	
	        return "login";
	    }	
	
    @RequestMapping(value="/musiclist")
    public String musicList(Model model) {	
        model.addAttribute("songs", srepository.findAll());
        return "musiclist";
    }
    
    //Index page
    @RequestMapping(value="/")
    public String index(Model model) {
    	return "index";
    }
    
   
        //List of all songs in JSON
        @RequestMapping(value="/songs", method = RequestMethod.GET)
        public @ResponseBody List<Song> songListRest() {
        	return (List<Song>) srepository.findAll();
    }
        //Get a song by ID
        @RequestMapping(value="/song/{id}", method = RequestMethod.GET)
        public @ResponseBody Optional<Song> findBookRest(@PathVariable("id") Long songId) {
        	return srepository.findById(songId);
        }
        
     //Controller for adding songs   
    @RequestMapping(value = "/add")
    public String addBook(Model model){
    	model.addAttribute("song", new Song());
    	model.addAttribute("genre", grepository.findAll());
        return "addsong";
    }     
    
    //Saves songs in database (when saving a new song)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Song song){
        srepository.save(song);
        return "redirect:musiclist";
    }    

    //Handles deletion of songs by ID (button on the page)
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteBook(@PathVariable("id") Long songId, Model model) {
    	srepository.deleteById(songId);
        return "redirect:../musiclist";
    }     
    
    //Handles editing of songs by ID (button on the page)
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editSong(@PathVariable("id") Long songId, Model model){
    	model.addAttribute("song", srepository.findById(songId));
    	model.addAttribute("genre", grepository.findAll());
        return "editsong";
    }
    
    //Saves songs in database (when editing existing songs)
    @RequestMapping(value = "/edit/editsave", method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
    public String edit(Long songId, Song song){
        srepository.save(song);
        return "redirect:../musiclist";
    	
}
    
    //For adding a genre
    @RequestMapping(value = "/addgenre")
    public String addGenre(Model model){
    	model.addAttribute("genre", new Genre());
    	return "addgenre";
    } 
    
    //Saves genre
    @RequestMapping(value = "/savegenre", method = RequestMethod.POST)
    public String savegenre(Genre genre){
        grepository.save(genre);
        return "redirect:musiclist";
    }    
    
	

}