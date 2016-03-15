import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
/**
 * Joshua Gihorski
 * MP3 Cleaner Project
 * CS338
 * 03/09/2016
 */
public class player extends JFrame
{	
	private static final long serialVersionUID = 1L;
	private engine myengine;
	private JButton myadd, myload, myauto, myremove;
	private JLabel title_label, artist_label, album_label, genre_label;
	private JTextField title_text, artist_text, album_text, genre_text;
	private JMenuItem helpbar, exitbar;
	private JTable mytable; 
	private JScrollPane myscroll;
	private DefaultTableModel mymodel;
	private ArrayList<File> myfiles;
	private int[] myselected;
	private int tutorial = 0;
	private ImageIcon icon;
	// create MP3 cleaner components
public player(){
	super("MP3 Cleaner"); // set program title
	JMenuBar menubar = new JMenuBar(); // create menu bar
    this.setJMenuBar(menubar);
    JMenu menulist = new JMenu("File"); // set menu title
    menubar.add(menulist); // add menu to menu bar
    helpbar = new JMenuItem("Help"); // create menu help item
    exitbar = new JMenuItem("Exit"); // create menu exit itm
    menulist.add(helpbar); // add help item to menu
    menulist.add(exitbar); // add exit item to menu
    // create four button functions
	myadd = new JButton("Add"); 
	myload = new JButton("Load");
	myauto = new JButton("Auto");
	myremove = new JButton("Remove");
	// create four textfields
	title_text = new JTextField(10);
	artist_text = new JTextField(10);
	album_text = new JTextField(10);
	genre_text = new JTextField(10);
	// create four labels
	title_label = new JLabel("Enter Title:");
	artist_label = new JLabel("Enter Artist:");
	album_label = new JLabel("Enter Album:");
	genre_label = new JLabel("Enter Genre:");
	// create music library table
	mytable = new JTable(new DefaultTableModel(new Object[]{"Song Title", "Artist", "Album", "Genre"},0){
		private static final long serialVersionUID = 4178973287235953165L;
		@Override
	    public boolean isCellEditable(int row, int column) {
	       return false;
	    }});
	mymodel = (DefaultTableModel) mytable.getModel();
	myscroll = new JScrollPane(mytable);
	// Absolute positioning
	setLayout(null);
	// add components to frame
	add(myadd);
	add(myload);
	add(myauto);
	add(myremove);
	add(myscroll);
	add(title_text);
	add(artist_text);
	add(album_text);
	add(genre_text);
	add(title_label);
	add(artist_label);
	add(album_label);
	add(genre_label);
	// set position for components in window
	myadd.setBounds(50, 440, 100, 50);
	myload.setBounds(50, 510, 100, 50);
	myauto.setBounds(650, 440, 100, 50);
	myremove.setBounds(650, 510, 100, 50);
	title_label.setBounds(250, 420, 150, 50);
	title_text.setBounds(225, 450, 150, 50);
	artist_label.setBounds(250, 495, 150, 50);
	artist_text.setBounds(225, 525, 150, 50);
	album_label.setBounds(450, 420, 150, 50);
	album_text.setBounds(425, 450, 150, 50);
	genre_label.setBounds(450, 495, 150, 50);
	genre_text.setBounds(425, 525, 150, 50);
	myscroll.setBounds(50, 10, 700, 400);
	// turn off buttons not being used
	myload.setEnabled(false);
	myauto.setEnabled(false);
	myremove.setEnabled(false);
	// create icon picture for pop up windows
	icon = new ImageIcon(player.class.getResource("icon.png"));
	// add button action listener inner class
	myadd.addActionListener(new ActionListener(){ 
		   public void actionPerformed(ActionEvent e){ 
			   if(tutorial == 0){ // simple tutorial first time display help pop up
				   getHelp(); // help pop up
			   }
			   JFileChooser filePicker = new JFileChooser(); // create a new file chooser
			   filePicker.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // set to folders we do not want single files in a table
			   int closeNum = filePicker.showOpenDialog(null); // open file picker
		        if(closeNum == JFileChooser.APPROVE_OPTION){ // check user option
		          File myfile = filePicker.getSelectedFile(); // get the music folder
		          myfiles = new ArrayList<File>(); // create data structure myfiles
		          getFiles(myfiles, myfile.getPath()); // recursive method to build data structure myfiles 
		          for(File f : myfiles){ // got files loop through build up jtable
		        	  myengine = new engine(); // create new mp3 file
		        	  myengine.setFile(f); // set the current file to the mp3 file just created
		        	  mymodel.addRow(new Object[]{myengine.getSong(),myengine.getArtist(),myengine.getAlbum(),myengine.getGenre()}); // read mp3 tag and add song to table 
		          }
		        myadd.setEnabled(false); // turn off add button
			    myremove.setEnabled(true); // turn on remove button
			    myload.setEnabled(true); // turn on load button
		        }
		        tutorial++; // increment to disable help pop up next time add is fired
		   }}); 
	// remove button action listener inner class
	myremove.addActionListener(new ActionListener(){ 
		   public void actionPerformed(ActionEvent e){
			   mymodel.setRowCount(0); // clear jtable rows
			   myfiles.clear(); // clear data structure myfiles
			   myadd.setEnabled(true); // turn on remove button
			   myload.setEnabled(false); // turn off load button
			   myremove.setEnabled(false); // turn off remove button
		   }}); 
	// load button action listener inner class
	myload.addActionListener(new ActionListener(){ 
		   public void actionPerformed(ActionEvent e){
			   	   myselected = mytable.getSelectedRows(); // store selected rows in jtable into array myselected
				   myload.setEnabled(false); // turn off load button
				   myauto.setEnabled(true); // turn on auto button
		   }}); 
	// auto button action listener inner class
	myauto.addActionListener(new ActionListener(){ 
		   public void actionPerformed(ActionEvent e){
			  int numChoice = JOptionPane.showOptionDialog(new JFrame(), "Selected files:" + myselected.length + "\nSave changes?", "Status Bar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, new Object[] {"Save", "Stop"}, JOptionPane.YES_OPTION);  // get user choice in pop up option window
			  if(numChoice == JOptionPane.YES_OPTION){  // if user clicks Save update table and data structure
				  if(!artist_text.getText().isEmpty()){ // check if artist text field is empty
					  for(int n : myselected){ // loop through selected songs
						  myengine.setFile(myfiles.get(n)); // create mp3 file tag
						  myengine.setArtist(artist_text.getText()); // set song artist with artist text field
						  myengine.savemp3(); // save song
					  }
				  }
				  if(!title_text.getText().isEmpty()){ // check if title text field is empty
						  for(int n : myselected){ // loop through selected songs
							  myengine.setFile(myfiles.get(n)); // create mp3 file tag
							  myengine.setSong(title_text.getText()); // set song title with title text field
							  myengine.savemp3(); // save song
						  }
					  }
				  if(!album_text.getText().isEmpty()){ // check if album text field is empty
						  for(int n : myselected){ // loop through selected songs
							  myengine.setFile(myfiles.get(n)); // create mp3 file tag
							  myengine.setAlbum(album_text.getText()); // set song album with album text field
							  myengine.savemp3(); // save song
						  }
					  }
				  if(!genre_text.getText().isEmpty()){ // check if genre text field is empty
						  for(int n : myselected){ // loop through selected songs
							  myengine.setFile(myfiles.get(n)); // create mp3 file tag
							  myengine.setGenre(genre_text.getText()); // set song genre with genre text field
							  myengine.savemp3(); // save song
						  }
					  }
			  mymodel.setRowCount(0); // refresh repaint jtable clear it out before rebuild it
			  for(File f : myfiles){ // loop through all the files we got
				  myengine = new engine(); // create new file to put into jtable
				  myengine.setFile(f); // create mp3 from file
				  mymodel.addRow(new Object[]{myengine.getSong(),myengine.getArtist(),myengine.getAlbum(),myengine.getGenre()}); // add the new song details back to the jtable
		      	}
			  } // else if user clicks stop or exit do nothing
			  myauto.setEnabled(false); // turn off auto button
			  myload.setEnabled(true); // turn on load button
		   }}); 
	helpbar.addActionListener(new ActionListener(){ 
		   public void actionPerformed(ActionEvent e){
			   getHelp(); // display help pop up
		   }}); 
	exitbar.addActionListener(new ActionListener(){ 
		   public void actionPerformed(ActionEvent e){
			   dispose(); // close GUI
			   System.exit(0); // exit system
		   }}); 
}
public void getHelp(){
	JOptionPane.showMessageDialog(null, "1. Add Button: Use this button first to load music into table.\n2. Load Button: Select music in table while holding the control button.\n3. Auto Button: Enter text into one of the textfields.\nSave will change the music. Stop will not change the music.\n4. Remove Button: Press this button any time to clear music in table.", "About MP3 Cleaner", JOptionPane.INFORMATION_MESSAGE, icon);
}
public void getFiles(ArrayList<File> myfiles, String mypath){
    File path = new File(mypath);  // create new file from path
    File[] allfiles = path.listFiles(); // get all files in current path
    for(File f : allfiles){ // loop through list of files
    	if(f.isFile()){ // check if this is a file
        	String s = f.getPath(); // get the path of the file
        	String num = s.substring(s.lastIndexOf(".")); // regex file extension
        	if(num.equals(".mp3")){ // check if file extension is .mp3
        		myfiles.add(f); // add mp3 file to array list
        	}
        }
        else if(f.isDirectory()){ // check if file is a directory
            getFiles(myfiles, f.getAbsolutePath()); // recursive call back to search for files in sub directory
        }
    }
  }
}