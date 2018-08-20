package com.dailynews.DailyNews.models.user;

import com.dailynews.DailyNews.models.comments.Comment.Comment;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLink;
import com.dailynews.DailyNews.models.user.role.Role;
import com.dailynews.DailyNews.models.user.salt.Salt;
import com.dailynews.DailyNews.models.user.salt.SaltDao;
import com.google.common.hash.Hashing;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue
	private int id;

	@NotNull
	private String username;

	@NotNull
	private String password;

	@ManyToOne
	private Role role;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<RssLink> rssLinks = new ArrayList<>();

	@OneToMany
	private List<Comment> comments;

	public User() {

	}

	public User(String username, String password, SaltDao sDao){
		password = hashPassword(password, sDao);
		this.username = username;
		this.password = password;
	}

	//<editor-fold desc="Rss Link utility methods">
	public void addRssLink(RssLink rssLink){
		if(rssLinks.contains(rssLink))
			return;

		rssLinks.add(rssLink);
	}

	public void removeRssLink(RssLink rssLink){
		rssLinks.remove(rssLink);
	}
	//</editor-fold>

	//<editor-fold desc="Password hashing utility methods">
	public boolean comparePassword(String otherPassword, SaltDao sDao){
		ArrayList<String> salts = getSalts(sDao);

		String passwordToCompare;
		for(String salt : salts){
			passwordToCompare = otherPassword;
			passwordToCompare += salt;
			passwordToCompare = Hashing.sha256().hashString(passwordToCompare, StandardCharsets.UTF_8).toString();

			if(getPassword().equals(passwordToCompare))
				return true;
		}

		return false;
	}

	public String hashPassword(String otherPassword, SaltDao sDao){
		otherPassword += getRandomSalt(sDao);
		String hashedPassword = Hashing.sha256().hashString(otherPassword, StandardCharsets.UTF_8).toString();

		return hashedPassword;
	}

	private ArrayList<String> getSalts(SaltDao sDao){
		ArrayList<String> retSalts = new ArrayList<>();
		System.out.println(sDao);
		for(Salt salt : sDao.findAll()){
			retSalts.add(salt.toString());
		}

		return retSalts;
	}

	private String getRandomSalt(SaltDao sDao){
		Random r = new Random();
		ArrayList<String> salts = getSalts(sDao);
		return salts.get(r.nextInt(salts.size()));
	}
	//</editor-fold>

	public int getId() {
		return id;
	}

	//<editor-fold desc="username Getters and Setters">
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	//</editor-fold>

	//<editor-fold desc="password Getters and Setters">
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	//</editor-fold>

	//<editor-fold desc="role Getters">
	public Role getRole(){
		return role;
	}

	public void setRole(Role role){
		this.role = role;
	}

	public void removeAllRssLinksById(ArrayList<Integer> removeRssIds) {

		for(Integer id : removeRssIds){
			for(RssLink link : rssLinks){
				if(id.equals(Integer.valueOf(link.getId()))){
					rssLinks.remove(link);
					break;
				}
			}
		}
	}
	//</editor-fold>

	//<editor-fold desc="rssLinks Getters and Setters">
	public void setRssLinks(List<RssLink> rssLinks) {
		this.rssLinks = rssLinks;
	}

	public List<RssLink> getRssLinks(){
		return rssLinks;
	}
	//</editor-fold>
}
