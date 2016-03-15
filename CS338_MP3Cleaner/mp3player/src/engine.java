import java.io.File;
import org.farng.mp3.MP3File;
import org.farng.mp3.id3.ID3v1;
/**
 * Joshua Gihorski
 * MP3 Cleaner Project
 * CS338
 * 03/09/2016
 */
public class engine {
	private File myfile = null; // Initialize new file
	private MP3File mp3file = null; // Initialize new mp3 file
	private ID3v1 tag = null; // Initialize new ID3v1 tag
	// genres are tagged with a number from 0 - 79 this array holds all known genres that can be edited
	private String genres[] =  {"Blues","Classic Rock","Country","Dance","Disco","Funk","Grunge","Hip-Hop","Jazz","Metal","New Age","Oldies","Other","Pop","R&B","Rap","Reggae","Rock","Techno","Industrial","Alternative","Ska","Death Metal","Pranks","Soundtrack","Euro-Techno","Ambient","Trip-Hop","Vocal","Jazz+Funk","Fusion","Trance","Classical","Instrumental","Acid","House","Game","Sound Clip","Gospel","Noise","AlternRock","Bass","Soul","Punk","Space","Meditative","Instrumental Pop","Instrumental Rock","Ethnic","Gothic","Darkwave","Techno-Industrial","Electronic","Pop-Folk","Eurodance","Dream","Southern Rock","Comedy","Cult","Gangsta","Top 40","Christian Rap","Pop/Funk","Jungle","Native American","Cabaret","New Wave","Psychadelic","Rave","Showtunes","Trailer","Lo-Fi","Tribal","Acid Punk","Acid Jazz","Polka","Retro","Musical","Rock & Roll","Hard Rock"};
	// create the mp3 file and tag from the file in the table
	public void setFile(File f){
		this.myfile = f; // store this file to the object
		this.mp3file = getMP3(myfile); // create the mp3 file for this object
		this.tag = getTag(mp3file, tag); // get the object for this tag with the mp3 file 
	}
	// create the mp3 file from the file
	public MP3File getMP3(File myfile){
		  try{
			  this.mp3file = new MP3File(myfile);
		  }
		  catch(Exception e){
			  System.out.println("Error getting MP3 " + e);
		  }
		  return mp3file;
	}
	// create the mp3 tag from the mp3 file
	public ID3v1 getTag(MP3File mymp3, ID3v1 mytag){
		  	try{
		  		mytag = mymp3.getID3v1Tag();
		  	}
		  	catch(Exception e){
		  		System.out.println("Error getting tag " + e);
		  	}
		  	return mytag;
	}   
	// save the mp3 file
	public void savemp3(){
		  try{
			  this.mp3file.save();
		  }
		  catch(Exception e){
			  System.out.println("Error saving file " + e);
		  }
	}
	// return the song title
	public String getSong(){
		return tag.getTitle();
	}
	// set the song title
	public void setSong(String a){
		tag.setTitle(a);
	}
	// return the song artist
	public String getArtist(){
		return tag.getArtist();
	}
	// set the song's artist
	public void setArtist(String a){
		tag.setArtist(a);
	}
	// return the song album
	public String getAlbum(){
		return tag.getAlbum();
	}
	// set the song's album
	public void setAlbum(String a){
		tag.setAlbumTitle(a);;
	}
	// return the song genre with the byte code using the genre array
	public String getGenre(){ // regex for numbers 0 - 79 to prevent index out of bounds in genre array
		if(tag.getSongGenre().matches("^[0-7]?[0-9]$")){ 
			return genres[Integer.parseInt(tag.getSongGenre())];
		}
		else{ // if genre is out of range of known genre list return nothing as a genre
			return "Nothing";
		}
	}
	// set the song's genre
	public void setGenre(String a){
		for(int i = 0; i < genres.length; i++){ // loop through all known genres list
			if(a.equalsIgnoreCase(genres[i])){ // check if genre in textfield is in genres list
				tag.setSongGenre(""+i); // set the tag for the new genre for this mp3 file
			}
		}
	}
}