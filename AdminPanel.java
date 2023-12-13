package MiniTwitter;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * AdminPanel uses Singleton Pattern to create a single instance to manage the
 * MiniTwitter Program.
 */
public class AdminPanel extends JFrame {

	private static AdminPanel instance = null;
	private DefaultMutableTreeNode jRoot, child;
	private JTree tree;
	private int totalGroups = 1;
	private int totalUsers = 0;
	private JPanel panel;
	private findUserCompVisitor userComp = new findUserCompVisitor();
	// private ValidatedVisitor validatedVisitor = new ValidatedVisitor();
	// private lastUpdateUserVisitor lastUpdated = new lastUpdatedUser();
	private Group rootGroup = new Group("root");
	private CompositeTree root = new CompositeTree("root", rootGroup);

	// Singleton Pattern
	public static AdminPanel getInstance() {
		if (instance == null) {
			instance = new AdminPanel();
		}
		return instance;
	}

	// Create default JFrame from Java Swing
	private AdminPanel() {
		// Create frame for content
		JPanel pane;
		this.setTitle("MiniTwitter Admin Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 600);
		pane = new JPanel();
		pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pane);
		pane.setLayout(new GridLayout(1, 0, 0, 0));

		// Create split pane of JFrame
		JSplitPane splitPane = new JSplitPane();
		pane.add(splitPane);

		// Set left panel of JFrame
		JPanel treePanel = new JPanel();
		splitPane.setLeftComponent(treePanel);

		// Set right panel of JFrame
		panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		// Add Jtree to left panel
		jRoot = new DefaultMutableTreeNode("root");
		tree = new JTree(jRoot);
		// Set default selection to be root in Jtree
		DefaultMutableTreeNode firstLeaf = ((DefaultMutableTreeNode) tree.getModel().getRoot()).getFirstLeaf();
		tree.setSelectionPath(new TreePath(firstLeaf.getPath()));
		treePanel.add(tree);

		// Add users and groups buttons panel
		addComponents();
		// Open individual user view panel
		userControlPanel();
		// Total number of users and groups panel
		userComponentCount();
		// Total number of messages panel
		messageCount();

		setVisible(true);
	}

	// Add users and groups from the panel
	private void addComponents() {
		JTextField userTextField;
		JTextField groupTextField;
		JPanel userPanel = new JPanel();
		panel.add(userPanel);
		userPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));

		// Text field to add new user
		userTextField = new JTextField();
		userPanel.add(userTextField);
		userTextField.setColumns(15);

		// Add new user button
		JButton addUserButton = new JButton("Add User");
		addUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Select the current element in the JTree
				DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode) tree.getSelectionPath()
						.getLastPathComponent();
				Group tempGroup = new Group(selectedElement.toString()); // Local Group used to compare to
																			// Groups in tree
				User user = new User(userTextField.getText()); // Create user object using the text field as
																// the UID
				CompositeTree selectedGroup = root.accept(userComp, tempGroup);
				// Add user to the group diretory if the user does not exit already
				if (selectedGroup != null && selectedGroup.getUserComponent() instanceof Group) {
					if (root.accept(userComp, user) != null) {
						System.out.println("Error. User already exits.");
						return;
					}
					root.accept(userComp, tempGroup).getChildren()
							.add(new CompositeTree(userTextField.getText(), user));
					// Update UI
					child = new DefaultMutableTreeNode(userTextField.getText());
					selectedElement.add(child);
					tree.updateUI();
					// Increment number of users
					totalUsers++;
				} else {
					System.out.println("Error can not add user to another user.");

				}
			}
		});

		addUserButton.setPreferredSize(new Dimension(200, 25));
		userPanel.add(addUserButton);

		// Panel for adding a new group
		JPanel groupPanel = new JPanel();
		panel.add(groupPanel);
		groupPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));

		groupTextField = new JTextField();
		groupPanel.add(groupTextField);
		groupTextField.setColumns(15);

		JButton addGroupButton = new JButton("Add Group");
		addGroupButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode) tree.getSelectionPath()
						.getLastPathComponent();
				Group tempGroup = new Group(selectedElement.toString()); // Local Group used to compare to
																			// Groups in tree
				Group userGroup = new Group(groupTextField.getText()); // Create userGroup object using the text
																		// field as the UID
				CompositeTree selectedGroup = root.accept(userComp, tempGroup);
				if (selectedGroup != null && selectedGroup.getUserComponent() instanceof Group) {
					if (root.accept(userComp, userGroup) != null) {
						System.out.println("Error: The definied group already exists.");
						return;
					}
					selectedGroup.getChildren().add(new CompositeTree(groupTextField.getText(), userGroup));
					// Update UI
					child = new DefaultMutableTreeNode(groupTextField.getText());
					selectedElement.add(child);
					tree.updateUI();
					// Increment Group total
					totalGroups++;
				} else {
					System.out.println("Error: Cannot add a group to a user.");
				}
			}
		});
		addGroupButton.setPreferredSize(new Dimension(200, 25));
		groupPanel.add(addGroupButton);
	}

	// Open user UI view for each user
	private void userControlPanel() {
		JPanel userView = new JPanel();
		panel.add(userView);
		userView.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));

		JButton openUserViewButton = new JButton("Open User View");
		openUserViewButton.setPreferredSize(new Dimension(150, 25));
		openUserViewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selectedElement = (DefaultMutableTreeNode) tree.getSelectionPath()
						.getLastPathComponent();
				CompositeTree selectedNode = root.accept(userComp, new User(selectedElement.toString()));
				// UserPanel userPanel = new UserPanel((User) selectedNode.getUserComponent(),
				// root);
			}
		});
		userView.add(openUserViewButton);

		// Assignment 3 - Last Updated User Button
		JButton lastUpdateButton = new JButton("Last Updated User");
		lastUpdateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("The last updated user is: " + root.accept(lastUpdated));
			}
		});
		userView.add(lastUpdateButton);

		// Assignment 3 - Validate User Button
		JButton validateButton = new JButton("Validate Users");
		validateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isValid = root.accept(validatedVisitor);
				if (isValid == true) {
					System.out.println("Usernames are valid.");
				} else {
					System.out.println("Usernames are not valid.");
				}
			}
		});
		userView.add(validateButton);
		validateButton.setPreferredSize(new Dimension(150, 25));
	}

	// Display total number of users and groups
	private void userComponentCount() {
		JPanel totalPanel = new JPanel();
		panel.add(totalPanel);
		totalPanel.setLayout(new GridLayout(2, 2, 0, 0));

		// Setup Panels
		JPanel userTotalPanel = new JPanel();
		totalPanel.add(userTotalPanel);
		JPanel groupTotalBtnPanel = new JPanel();
		totalPanel.add(groupTotalBtnPanel);
		JPanel userLabelPanel = new JPanel();
		totalPanel.add(userLabelPanel);
		JPanel groupTotalLabelPanel = new JPanel();
		totalPanel.add(groupTotalLabelPanel);

		// Set labels
		JLabel lblNewLabel = new JLabel("Total Users: " + totalUsers);
		JLabel lblNewLabel_1 = new JLabel("Total Groups: " + totalGroups);
		userLabelPanel.add(lblNewLabel);
		groupTotalLabelPanel.add(lblNewLabel_1);

		// Button to update user totals
		JButton userTotal_button = new JButton("Show User Total");
		userTotal_button.setPreferredSize(new Dimension(200, 25));
		userTotal_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel.setText("Total Users: " + totalUsers);
				lblNewLabel.updateUI();
			}
		});
		userTotalPanel.add(userTotal_button);

		// Button to update amount of groups
		JButton groupTotal_button = new JButton("Show Group Total");
		groupTotal_button.setPreferredSize(new Dimension(200, 25));
		groupTotal_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel_1.setText("Total Groups: " + totalGroups);
				lblNewLabel_1.updateUI();
			}
		});
		groupTotalBtnPanel.add(groupTotal_button);
	}

	// Display total message count and positive message ratio
	private void messageCount() {
		JPanel message_Panel = new JPanel();
		JLabel msgTotal_label = new JLabel("Total Message: 0");
		JLabel posPerc_label = new JLabel("Positive ratio: ");
		panel.add(message_Panel);
		message_Panel.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel msgTotalbtn_panel = new JPanel();
		message_Panel.add(msgTotalbtn_panel);

		JButton messageTotal_button = new JButton("Show Messages Total");
		messageTotal_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int msgCount = root.countMsg(root)[0];
				msgTotal_label.setText("Total Messages: " + msgCount);
				msgTotal_label.updateUI();
			}
		});
		msgTotalbtn_panel.add(messageTotal_button);
		messageTotal_button.setFont(new Font("Tahoma", Font.BOLD, 11));
		messageTotal_button.setPreferredSize(new Dimension(200, 25));

		JPanel posPercBtn_panel = new JPanel();
		message_Panel.add(posPercBtn_panel);

		JButton posPerc_button = new JButton("Show Positive Percentage");
		posPercBtn_panel.add(posPerc_button);
		posPerc_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float ratio = (float) (root.countMsg(root)[1]) / root.countMsg(root)[0];
				ratio = (float) Math.round(ratio * 10000) / 100;
				posPerc_label.setText("Positive ratio: " + Float.toString(ratio) + "%");
				posPerc_label.updateUI();
			}
		});
		posPerc_button.setPreferredSize(new Dimension(200, 25));

		JPanel totalMsgLbl_panel = new JPanel();
		message_Panel.add(totalMsgLbl_panel);

		totalMsgLbl_panel.add(msgTotal_label);

		JPanel posPercLbl_panel = new JPanel();
		message_Panel.add(posPercLbl_panel);

		posPercLbl_panel.add(posPerc_label);
	}
}