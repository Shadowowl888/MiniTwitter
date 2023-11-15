package MiniTwitter;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;

public class userPanel extends JFrame implements Observer {

	private JPanel contentPane;
	private JTextField userTxtField;
	private JTextField message_box;
	private User user;
	private Tree root;
	private findUserCompVisitor findUserC = new findUserCompVisitor();
	private JList following_JList;
	private JList newsFeed_Jlist;

	/**
	 * @param u    the userName object of the user panel
	 * @param Tree the root node of the tree (root userGroup)
	 */
	public userPanel(User u, Tree tree) {
		user = u;
		root = tree;
		makeGui();
	}

	// Return the user object of this class
	private User getUser() {
		return user;
	}

	// Call methods to setup the UI
	private void makeGui() {
		setTitle("User Panel: " + user.getUID());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		timePanel();
		followUser();
		followingList();
		tweetPanel();
		newsFeed();
		setVisible(true);
	}

	// Create UI to allow for users to be followed
	private void followUser() {
		JPanel follow_panel = new JPanel();
		contentPane.add(follow_panel);
		follow_panel.setLayout(new BoxLayout(follow_panel, BoxLayout.X_AXIS));
		follow_panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		JPanel userTxt_panel = new JPanel();
		follow_panel.add(userTxt_panel);

		userTxtField = new JTextField();
		userTxtField.setText("Enter user");
		userTxt_panel.add(userTxtField);
		userTxtField.setColumns(10);

		JPanel followBtn_panel = new JPanel();
		follow_panel.add(followBtn_panel);

		JButton followButton = new JButton("Follow User");
		followButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User u = new User(null);
				try {
					u = (User) root.accept(findUserC, new User(userTxtField.getText())).getUserComponent();
					u.attach(getUser().getNewsFeed());
					user.follow(u);
				} catch (Exception userNotFoundException) {
					System.out.println("User does not exist");
				}
				// Set the following list data and update the UI
				following_JList.setListData(getUser().getFollowingNames());
				following_JList.updateUI();
			}
		});
		followButton.setPreferredSize(new Dimension(125, 20));
		followBtn_panel.add(followButton);
	}

	// Create UI to show the following list of the user
	private void followingList() {
		JPanel follower_panel;
		follower_panel = new JPanel();
		contentPane.add(follower_panel);
		follower_panel.setLayout(new BoxLayout(follower_panel, BoxLayout.Y_AXIS));

		JPanel ftitle_panel;
		ftitle_panel = new JPanel();
		ftitle_panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
		follower_panel.add(ftitle_panel);
		ftitle_panel.setLayout(new BoxLayout(ftitle_panel, BoxLayout.X_AXIS));

		JLabel followingLabel = new JLabel("Following");
		followingLabel.setFont(new Font("Arial", Font.BOLD, 13));
		ftitle_panel.add(followingLabel);

		JPanel flist_panel;
		flist_panel = new JPanel();
		follower_panel.add(flist_panel);
		flist_panel.setLayout(new GridLayout(0, 1, 0, 0));

		// Add JList to scrollpane
		JScrollPane scrollPane = new JScrollPane();
		flist_panel.add(scrollPane);
		following_JList = new JList();
		scrollPane.setViewportView(following_JList);
		following_JList.setFont(new Font("Arial", Font.PLAIN, 12));
		following_JList.setListData(getUser().getFollowingNames());
	}

	// Allow the user to make tweets
	private void tweetPanel() {
		JPanel tweet_panel = new JPanel();
		contentPane.add(tweet_panel);
		tweet_panel.setLayout(new BoxLayout(tweet_panel, BoxLayout.X_AXIS));
		tweet_panel.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0));

		JPanel msgBox_panel = new JPanel();
		tweet_panel.add(msgBox_panel);

		message_box = new JTextField();
		message_box.setHorizontalAlignment(SwingConstants.LEFT);
		msgBox_panel.add(message_box);
		message_box.setColumns(10);

		JPanel tweetBtn_panel = new JPanel();
		tweet_panel.add(tweetBtn_panel);

		JButton tweetBtn = new JButton("Tweet");
		tweetBtn.setPreferredSize(new Dimension(100, 20));
		tweetBtn.addActionListener(new ActionListener() {
			// Reverse the news feed to show recent messages on top and older ones on bottom
			public void actionPerformed(ActionEvent e) {
				getUser().tweet(message_box.getText());
				// Update tweet box UI with updates list of tweets
				update("");
			}
		});
		tweetBtn_panel.add(tweetBtn);
	}

	// Set the UI to showcase the newsfeed of the user
	private void newsFeed() {
		JPanel newsFeed_panel = new JPanel();
		contentPane.add(newsFeed_panel);
		newsFeed_panel.setLayout(new BoxLayout(newsFeed_panel, BoxLayout.Y_AXIS));

		JPanel nfTitle_panel = new JPanel();
		newsFeed_panel.add(nfTitle_panel);
		nfTitle_panel.setLayout(new BoxLayout(nfTitle_panel, BoxLayout.X_AXIS));
		nfTitle_panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

		JLabel newsFeedLbl = new JLabel("News Feed");
		newsFeedLbl.setFont(new Font("Arial", Font.BOLD, 13));
		nfTitle_panel.add(newsFeedLbl);

		JPanel nfList_panel = new JPanel();
		newsFeed_panel.add(nfList_panel);
		nfList_panel.setLayout(new GridLayout(0, 1, 0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		nfList_panel.add(scrollPane_1);

		newsFeed_Jlist = new JList();
		scrollPane_1.setViewportView(newsFeed_Jlist);
		newsFeed_Jlist.setFont(new Font("Arial", Font.PLAIN, 12));
		// Reverse the newsFeed so that most recent messages are shown on top
		ArrayList<String> userNewsFeed = getUser().getNewsFeed().getMessages();
		getUser().getNewsFeed().attach(this);
		ArrayList<String> revArrayList = new ArrayList<String>();
		for (int i = userNewsFeed.size() - 1; i >= 0; i--) {
			revArrayList.add(userNewsFeed.get(i));
		}
		newsFeed_Jlist.setListData(revArrayList.toArray());
	}

	// Assignment 3
	private void timePanel() {
		JPanel updatePanel = new JPanel();
		contentPane.add(updatePanel);
		updatePanel.setLayout(new GridLayout(1, 0, 0, 0));
		// Set time created
		JPanel timeCreated_panel = new JPanel();
		updatePanel.add(timeCreated_panel);

		JLabel timeCreated_label = new JLabel("");
		timeCreated_panel.add(timeCreated_label);
		// Convert time to readable format
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy HH:mm:ss");
		Date resultdate = new Date(user.getCreationTime());
		timeCreated_label.setText(sdf.format(resultdate));

		// Set most recent update time
		JPanel lastUpdate_panel = new JPanel();
		updatePanel.add(lastUpdate_panel);

		JLabel lastUpdate_label = new JLabel("");
		Date updateTime = new Date(user.getLastUpdateTime());
		lastUpdate_label.setText(sdf.format(updateTime));
		lastUpdate_panel.add(lastUpdate_label);
	}

	// Update the user UI tweets when changes are made in news feed
	@Override
	public void update(String tweet) {
		ArrayList<String> userNewsFeed = getUser().getNewsFeed().getMessages();
		ArrayList<String> revArrayList = new ArrayList<String>();
		for (int i = userNewsFeed.size() - 1; i >= 0; i--) {
			revArrayList.add(userNewsFeed.get(i));
		}
		newsFeed_Jlist.setListData(revArrayList.toArray());
		newsFeed_Jlist.updateUI();
	}
}