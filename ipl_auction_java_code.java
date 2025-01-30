import java.sql.* ;
import java.awt.* ;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.util.* ;
import javax.swing.* ;


class auction extends JFrame implements ActionListener{
	int i = 1 ;
	int curr_pot = 0  ;
	JLabel l1,l2,l3,l4,l5,l6 ;
	JTextField f1,f2,f3,f4,f5,f6 ;
	JButton b1,b2,b3,b4,b5 ;
	JComboBox<String> dropdown ;
	
	auction(){
		this.setSize(500,500);
		this.setTitle("Auction Interface");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(9,2));
		
		l1 = new JLabel("Pot number") ;
		l2 = new JLabel("Player Name") ;
		l3 = new JLabel("Player rating") ;
		l4 = new JLabel("Player Role") ;
		l5 = new JLabel("Player Base Price") ;
		l6 = new JLabel("PLayer Team") ;
		
		f1 = new JTextField() ;
		f2 = new JTextField() ;
		f3 = new JTextField() ;
		f4 = new JTextField() ;
		f5 = new JTextField() ;
		f6 = new JTextField() ;
		
		b1 = new JButton("Incrementer") ;
		b2 = new JButton("Add player to team") ;
		b3 = new JButton("Previous Player") ;
		b4 = new JButton("Next Player") ;
		b5 = new JButton("Delete Player") ;
		
		dropdown = new JComboBox<>();
        dropdown.addItem("");
        dropdown.addItem("csk");
        dropdown.addItem("dc");
        dropdown.addItem("gt");
        dropdown.addItem("kkr");
        dropdown.addItem("lsg");
        dropdown.addItem("mi");
        dropdown.addItem("pbks");
        dropdown.addItem("rcb");
        dropdown.addItem("rr");
        dropdown.addItem("srh");
        
		add(l1) ; add(f1) ;
		add(l2) ; add(f2) ;
		add(l3) ; add(f3) ;
		add(l4) ; add(f4) ;
		add(l5) ; add(f5) ;
		add(l6) ; add(dropdown) ;
		
		add(b1) ; add(b2) ;
		add(b3) ; add(b4) ;
		add(b5) ;
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		
		this.setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getActionCommand().equals("Next Player")) {
		    Connection c = null;
		    int pot = Integer.parseInt(f1.getText());
		    try {
		        c = get_connection();

		        // Reset for a new pot
		        if (curr_pot != pot) {
		            i = 0;
		            curr_pot = pot;
		        }

		        // Query players for the given pot
		        PreparedStatement p1 = c.prepareStatement("SELECT * FROM players WHERE pot = ? ");
		        p1.setInt(1, pot);
		        ResultSet r = p1.executeQuery();

		        // Find the next player
		        int current = 0;
		        boolean playerFound = false;
		        while (r.next()) {
		            if (current == i) {
		                String name = r.getString("pName");
		                String role = r.getString("Role");
		                int rating = r.getInt("rating");
		                double price = r.getDouble("basePrice");

		                // Update UI with player details
		                f2.setText(name);
		                f3.setText("" + rating);
		                f4.setText(role);
		                f5.setText("" + price);

		                playerFound = true;
		                break;
		            }
		            current++;
		        }

		        if (playerFound) {
		            i++; // Move to the next player
		        } else {
		            System.out.println("No more players available.");
		        }

		    } catch (Exception e4) {
		        System.out.println(e4);
		    }
		}
		
		

		if (e.getActionCommand().equals("Previous Player")) {
		    Connection c = null;
		    int pot = Integer.parseInt(f1.getText());
		    try {
		        c = get_connection();

		        // Reset for a new pot
		        if (curr_pot != pot) {
		            i = 0;
		            curr_pot = pot;
		        }

		        if (i > 0) {
		            i--; // Move to the previous player
		        }

		        // Query players for the given pot
		        PreparedStatement p1 = c.prepareStatement("SELECT * FROM players WHERE pot = ? ");
		        p1.setInt(1, pot);
		        ResultSet r = p1.executeQuery();

		        // Find the previous player
		        int current = 0;
		        boolean playerFound = false;
		        while (r.next()) {
		            if (current == i) {
		                String name = r.getString("pName");
		                String role = r.getString("Role");
		                int rating = r.getInt("rating");
		                double price = r.getDouble("basePrice");

		                // Update UI with player details
		                f2.setText(name);
		                f3.setText("" + rating);
		                f4.setText(role);
		                f5.setText("" + price);

		                playerFound = true;
		                break;
		            }
		            current++;
		        }

		        if (!playerFound) {
		            System.out.println("No previous players available.");
		        }

		    } catch (Exception e4) {
		        System.out.println(e4);
		    }
		}

		if(e.getActionCommand().equals("Add player to team")) {
			Connection c = null ;
			String name = f2.getText() ;
			int rating = Integer.parseInt(f3.getText()) ;
			String team = (String) dropdown.getSelectedItem();
			Double price = Double.parseDouble(f5.getText()) ;
			try {
				c = get_connection(); 
				
				PreparedStatement p1 = c.prepareStatement("select role from Players where pName=? and rating = ?") ;
				p1.setString(1, name) ;
				p1.setInt(2, rating) ;
				ResultSet r = p1.executeQuery();
				String role ="" ;
				while(r.next()) {
					role += r.getString("Role") ;
				}
				
				
				if(team.equals("rcb")) {
					PreparedStatement p = c.prepareStatement("insert into rcb (pName,bidPrice,rating,Role) values (?,?,?,?)") ;
					p.setString(1, name) ;
					p.setDouble(2, price) ;
					p.setInt(3, rating) ;
					p.setString(4, role) ;
					p.executeUpdate() ;
					JOptionPane.showMessageDialog(null,"player added successfully to rcb") ;
				}
				if(team.equals("dc")) {
					PreparedStatement p = c.prepareStatement("insert into dc (pName,bidPrice,rating,Role) values (?,?,?,?)") ;
					p.setString(1, name) ;
					p.setDouble(2, price) ;
					p.setInt(3, rating) ;
					p.setString(4, role) ;
					p.executeUpdate() ;
					JOptionPane.showMessageDialog(null,"player added successfully to dc") ;
				}
				if(team.equals("csk")) {
					PreparedStatement p = c.prepareStatement("insert into csk (pName,bidPrice,rating,Role) values (?,?,?,?)") ;
					p.setString(1, name) ;
					p.setDouble(2, price) ;
					p.setInt(3, rating) ;
					p.setString(4, role) ;
					p.executeUpdate() ;
					JOptionPane.showMessageDialog(null,"player added successfully to csk") ;
				}
				if(team.equals("gt")) {
					PreparedStatement p = c.prepareStatement("insert into gt (pName,bidPrice,rating,Role) values (?,?,?,?)") ;
					p.setString(1, name) ;
					p.setDouble(2, price) ;
					p.setInt(3, rating) ;
					p.setString(4, role) ;
					p.executeUpdate() ;
					JOptionPane.showMessageDialog(null,"player added successfully to gt") ;
				}
				if(team.equals("kkr")) {
					PreparedStatement p = c.prepareStatement("insert into kkr (pName,bidPrice,rating,Role) values (?,?,?,?)") ;
					p.setString(1, name) ;
					p.setDouble(2, price) ;
					p.setInt(3, rating) ;
					p.setString(4, role) ;
					p.executeUpdate() ;
					JOptionPane.showMessageDialog(null,"player added successfully to kkr") ;
				}
				if(team.equals("lsg")) {
					PreparedStatement p = c.prepareStatement("insert into lsg (pName,bidPrice,rating,Role) values (?,?,?,?)") ;
					p.setString(1, name) ;
					p.setDouble(2, price) ;
					p.setInt(3, rating) ;
					p.setString(4, role) ;
					p.executeUpdate() ;
					JOptionPane.showMessageDialog(null,"player added successfully to lsg") ;
				}
				if(team.equals("mi")) {
					PreparedStatement p = c.prepareStatement("insert into mi (pName,bidPrice,rating,Role) values (?,?,?,?)") ;
					p.setString(1, name) ;
					p.setDouble(2, price) ;
					p.setInt(3, rating) ;
					p.setString(4, role) ;
					p.executeUpdate() ;
					JOptionPane.showMessageDialog(null,"player added successfully to mi") ;
				}
				
				if(team.equals("pbks")) {
					PreparedStatement p = c.prepareStatement("insert into pbks (pName,bidPrice,rating,Role) values (?,?,?,?)") ;
					p.setString(1, name) ;
					p.setDouble(2, price) ;
					p.setInt(3, rating) ;
					p.setString(4, role) ;
					p.executeUpdate() ;
					JOptionPane.showMessageDialog(null,"player added successfully to pbks") ;
				}
				if(team.equals("rr")) {
					PreparedStatement p = c.prepareStatement("insert into rr (pName,bidPrice,rating,Role) values (?,?,?,?)") ;
					p.setString(1, name) ;
					p.setDouble(2, price) ;
					p.setInt(3, rating) ;
					p.setString(4, role) ;
					p.executeUpdate() ;
					JOptionPane.showMessageDialog(null,"player added successfully to rr") ;
				}
				if(team.equals("srh")) {
					PreparedStatement p = c.prepareStatement("insert into srh (pName,bidPrice,rating,Role) values (?,?,?,?)") ;
					p.setString(1, name) ;
					p.setDouble(2, price) ;
					p.setInt(3, rating) ;
					p.setString(4, role) ;
					p.executeUpdate() ;
					JOptionPane.showMessageDialog(null,"player added successfully to srh") ;
				}
				
				
			}
			catch(Exception e1) {
				System.out.println(e1) ;
			}
		}
		
		if(e.getActionCommand().equals("Delete Player")) {
			Connection c = null ;
			String name = f2.getText() ;
			String team = (String) dropdown.getSelectedItem();
			try {
				c = get_connection(); 
				
//				PreparedStatement p1 = c.prepareStatement("select role from Players where pName=? and rating = ?") ;
//				p1.setString(1, name) ;
//				p1.setInt(2, rating) ;
//				ResultSet r = p1.executeQuery();
//				String role ="" ;
//				while(r.next()) {
//					role += r.getString("Role") ;
//				}
				
				
				if(team.equals("rcb")) {
					PreparedStatement p = c.prepareStatement("delete from rcb where pName = ?") ;
					p.setString(1, name) ;
					p.executeUpdate() ;
					JOptionPane.showMessageDialog(null,"player removed") ;
				}
				
				if(team.equals("dc")) {
					PreparedStatement p = c.prepareStatement("delete from dc where pName = ?") ;
					p.setString(1, name) ;
					p.executeUpdate() ;
					JOptionPane.showMessageDialog(null,"player removed") ;
				}
				
				if(team.equals("csk")) {
					PreparedStatement p = c.prepareStatement("delete from csk where pName = ?") ;
					p.setString(1, name) ;
					p.executeUpdate() ;
					JOptionPane.showMessageDialog(null,"player removed") ;
				}
				
				if(team.equals("gt")) {
					PreparedStatement p = c.prepareStatement("delete from gt where pName = ?") ;
					p.setString(1, name) ;
					p.executeUpdate() ;
					JOptionPane.showMessageDialog(null,"player removed") ;
				}
				
				if(team.equals("kkr")) {
					PreparedStatement p = c.prepareStatement("delete from kkr where pName = ?") ;
					p.setString(1, name) ;
					p.executeUpdate() ;
					JOptionPane.showMessageDialog(null,"player removed") ;
				}
				
				if(team.equals("lsg")) {
					PreparedStatement p = c.prepareStatement("delete from lsg where pName = ?") ;
					p.setString(1, name) ;
					p.executeUpdate() ;
					JOptionPane.showMessageDialog(null,"player removed") ;
				}
				
				if(team.equals("mi")) {
					PreparedStatement p = c.prepareStatement("delete from mi where pName = ?") ;
					p.setString(1, name) ;
					p.executeUpdate() ;
					JOptionPane.showMessageDialog(null,"player removed") ;
				}
				
				if(team.equals("pbks")) {
					PreparedStatement p = c.prepareStatement("delete from pbks where pName = ?") ;
					p.setString(1, name) ;
					p.executeUpdate() ;
					JOptionPane.showMessageDialog(null,"player removed") ;
				}
				
				if(team.equals("rr")) {
					PreparedStatement p = c.prepareStatement("delete from rr where pName = ?") ;
					p.setString(1, name) ;
					p.executeUpdate() ;
					JOptionPane.showMessageDialog(null,"player removed") ;
				}
				
				if(team.equals("srh")) {
					PreparedStatement p = c.prepareStatement("delete from srh where pName = ?") ;
					p.setString(1, name) ;
					p.executeUpdate() ;
					JOptionPane.showMessageDialog(null,"player removed") ;
				}
				
				
				
			}
			catch(Exception e1) {
				System.out.println(e1) ;
			}
		}
		
		if (e.getActionCommand().equals("Incrementer")) {
		    Double price = Double.parseDouble(f5.getText());
		    try {
		        if (price < 2) {
		            price += 0.25;
		        } else if (price < 10) {
		            price += 0.5;
		        } else {
		            price += 1;
		        }

		        Connection c = null;
		        c = get_connection();
		        PreparedStatement p3 = c.prepareStatement("select sum(bidPrice) from csk");
		        ResultSet r41 = p3.executeQuery();
		        Double curr = 0.0;
		        while (r41.next()) {
		            curr = r41.getDouble("sum(bidPrice)");
		        }

		        if (curr > 100) {
		            throw new Exception("No money left!"); // Throw an exception with a custom message
		        }

		        String p = "" + price;
		        f5.setText(p);
		    } catch (Exception e1) {
		        // Display the exception message in a JOptionPane
		        JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		    }
		}

	}
	Connection get_connection() throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection c = null ;
		try {
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/auction","HP","Lihkin*27405") ;
		}
		catch(Exception e) {
			System.out.println(e) ;
		}
		return c ;
	}
}

class team_standing extends JFrame implements ActionListener{
	JButton b1 ;
	JLabel l1,l2 ,l3,l4;
	JTextField[] f ;
    
    team_standing(){
    	this.setSize(500,500);
		this.setTitle("Team_standings interface");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(12,4));
		
		l1 = new JLabel("Team Name") ;
		l2 = new JLabel("Team Rating") ;
		l3 = new JLabel("Money spent") ;
		l4 = new JLabel("Money left") ;
		
		b1 = new JButton("Show standing") ;
		
		
		f = new JTextField[43] ;
		
		for (int i = 0; i < 43; i++) {
            f[i] = new JTextField(); 
        }
		
		add(b1) ; add(f[0]) ;
		add(f[1]) ; add(f[2]) ;
		add(l1) ; add(l2) ;
		add(l3) ; add(l4) ;
		for(int i=3 ;i<43 ;i+=2) {
			add(f[i]); add(f[i+1]);
		}
		
		this.setVisible(true);
		b1.addActionListener(this);
		
	}
    
	public void actionPerformed(ActionEvent e) {
	    	if(e.getActionCommand().equals("Show standing")) {
	    		Connection c = null ;
	    		try {
	    			c = get_connection(); 
					
	    			double[] ans = new double[10]; 
	    			double[] rate = new double[10] ;
	    			String []team = {"csk","dc","gt","kkr","lsg","mi","pbks","rcb","rr","srh"} ;
	    			
	    			for(int i=0 ;i<10 ;i++) {
	    				ans[i] = 0 ;
	    			}
	    			ResultSet r31,r32,r33,r34,r35,r36,r37,r38,r39,r40,r41,r42,r43,r44,r45,r46,r47,r48,r49,r50 ;
					PreparedStatement p2 = c.prepareStatement("select sum(rating) from csk ") ;
					PreparedStatement p3 = c.prepareStatement("select sum(bidPrice) from csk") ;
					r31 = p2.executeQuery() ;
					r41 = p3.executeQuery();
					while(r31.next()) {
						ans[0] = r31.getDouble("sum(rating)") ;
					}
					while(r41.next()) {
						rate[0] = r41.getDouble("sum(bidPrice)") ;
					}
					
					PreparedStatement p21 = c.prepareStatement("select sum(rating) from dc") ;
					PreparedStatement p31 = c.prepareStatement("select sum(bidPrice) from dc") ;
					r32 = p21.executeQuery() ;
					r42 = p31.executeQuery();
					while(r32.next()) {
						ans[1] = r32.getDouble("sum(rating)") ;
					}
					while(r42.next()) {
						rate[1] = r42.getDouble("sum(bidPrice)") ;
					}
					
					PreparedStatement p22 = c.prepareStatement("select sum(rating) from gt") ;
					PreparedStatement p32 = c.prepareStatement("select sum(bidPrice) from gt") ;
					r33 = p22.executeQuery() ;
					r43 = p32.executeQuery();
					while(r33.next()) {
						ans[2] = r33.getDouble("sum(rating)") ;
					}
					while(r43.next()) {
						rate[2] = r43.getDouble("sum(bidPrice)") ;
					}
					
					
					PreparedStatement p23 = c.prepareStatement("select sum(rating) from kkr") ;
					PreparedStatement p33 = c.prepareStatement("select sum(bidPrice) from kkr") ;
					r34 = p23.executeQuery() ;
					r44 = p33.executeQuery();
					while(r34.next()) {
						ans[3] = r34.getDouble("sum(rating)") ;
					}
					while(r44.next()) {
						rate[3] = r44.getDouble("sum(bidPrice)") ;
					}
					
					PreparedStatement p24 = c.prepareStatement("select sum(rating) from lsg") ;
					PreparedStatement p34 = c.prepareStatement("select sum(bidPrice) from lsg") ;
					r35 = p24.executeQuery() ;
					r45 = p34.executeQuery();
					while(r35.next()) {
						ans[4] = r35.getDouble("sum(rating)") ;
					}
					while(r45.next()) {
						rate[4] = r45.getDouble("sum(bidPrice)") ;
					}
					
					PreparedStatement p25 = c.prepareStatement("select sum(rating) from mi") ;
					PreparedStatement p35 = c.prepareStatement("select sum(bidPrice) from mi") ;
					r36 = p25.executeQuery() ;
					r46 = p35.executeQuery();
					while(r36.next()) {
						ans[5] = r36.getDouble("sum(rating)") ;
					}
					while(r46.next()) {
						rate[5] = r46.getDouble("sum(bidPrice)") ;
					}
					
					PreparedStatement p26 = c.prepareStatement("select sum(rating) from pbks ") ;
					PreparedStatement p36 = c.prepareStatement("select sum(bidPrice) from pbks") ;
					r37 = p26.executeQuery() ;
					r47 = p36.executeQuery();
					while(r37.next()) {
						ans[6] = r37.getDouble("sum(rating)") ;
					}
					while(r47.next()) {
						rate[6] = r47.getDouble("sum(bidPrice)") ;
					}
					
					PreparedStatement p27 = c.prepareStatement("select sum(rating) from rcb") ;
					PreparedStatement p37 = c.prepareStatement("select sum(bidPrice) from rcb") ;
					r38 = p27.executeQuery() ;
					r48 = p37.executeQuery();
					while(r38.next()) {
						ans[7] = r38.getDouble("sum(rating)") ;
					}
					while(r48.next()) {
						rate[7] = r48.getDouble("sum(bidPrice)") ;
					}
					
					PreparedStatement p28 = c.prepareStatement("select sum(rating) from rr") ;
					PreparedStatement p38 = c.prepareStatement("select sum(bidPrice) from rr") ;
					r39 = p28.executeQuery() ;
					r49 = p38.executeQuery();
					while(r39.next()) {
						ans[8] = r39.getDouble("sum(rating)") ;
					}
					while(r49.next()) {
						rate[8] = r49.getDouble("sum(bidPrice)") ;
					}
					
					PreparedStatement p29 = c.prepareStatement("select sum(rating) from srh ") ;
					PreparedStatement p39 = c.prepareStatement("select sum(bidPrice) from srh") ;
					r40 = p29.executeQuery() ;
					r50 = p39.executeQuery();
					while(r40.next()) {
						ans[9] = r40.getDouble("sum(rating)") ;
					}
					while(r50.next()) {
						rate[9] = r50.getDouble("sum(bidPrice)") ;
					}
					
					for(int i=0 ;i<10 ;i++) {
						for(int j=i ;j<10 ;j++) {
							if(ans[i]<ans[j]) {
								double temp = ans[i] ;
								ans[i] = ans[j] ;
								ans[j] = temp ;
								String temp2 = team[i] ;
								team[i] = team[j] ;
								team[j] = temp2 ;
								double temp3 = rate[i] ;
								rate[i] = rate[j] ;
								rate[j] = temp3 ;
							}
						}
					}
					
					int j = 3 ;
					for(int i=0 ;i<10 ;i++) {
						f[j].setText(team[i]);
						f[j+1].setText("" + ans[i]);
						f[j+2].setText("" + rate[i]);
						f[j+3].setText("" + (100-rate[i]));
						j +=4 ;
					}
					//String s = Double.toString(ans); 
					//f[0].setText(s);
	    		}
	    		catch(Exception e3) {
	    			System.out.println(e3) ;
	    		}
	    		
	    	}
	    	
	}
	Connection get_connection() throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection c = null ;
		try {
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/auction","HP","Lihkin*27405") ;
		}
		catch(Exception e) {
			System.out.println(e) ;
		}
		return c ;
	}
}

class team extends JFrame implements ActionListener{
	JLabel l1,l2,l3,l4,l5,l6,l7,l8 ;
	JButton b1 ;
	JTextField[] f ;
	JComboBox<String> dropdown ;
    
    team(){
    	this.setSize(500,500);
		this.setTitle("Team interface");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(17,4));
		
		l1 = new JLabel("Team Name") ;
		l2 = new JLabel("Player Name") ;
		l3 = new JLabel("Player Price") ;
		l4 = new JLabel("Player Rating") ;
		l5 = new JLabel("Team Rating") ;
		l6 = new JLabel("Player Role") ;
		l7 = new JLabel("") ;
		l8 = new JLabel("") ;
		
		dropdown = new JComboBox<>();
        dropdown.addItem("");
        dropdown.addItem("csk");
        dropdown.addItem("dc");
        dropdown.addItem("gt");
        dropdown.addItem("kkr");
        dropdown.addItem("lsg");
        dropdown.addItem("mi");
        dropdown.addItem("pbks");
        dropdown.addItem("rcb");
        dropdown.addItem("rr");
        dropdown.addItem("srh");
        
		
		b1 = new JButton("Show team") ;
		
		f = new JTextField[59] ;
		
		for (int i = 0; i < 59; i++) {
            f[i] = new JTextField(); 
        }
		
		add(l1) ; add(dropdown); ; add(b1) ; add(l7) ;
		add(l2) ; add(l3) ; add(l4) ; add(l6) ;
		for(int i=1 ;i<57 ;i+=4) {
			add(f[i]); add(f[i+1]); add(f[i+2]); add(f[i+3]);
		}
		
		add(l5) ; add(f[57]) ; add(f[58]) ; add(l8) ;
		
		b1.addActionListener(this) ;
		this.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
    	if(e.getActionCommand().equals("Show team")) {
        	Connection c = null ;
    		String team = (String) dropdown.getSelectedItem();
    		try {
				c = get_connection(); 
				
				if(team.equals("rcb")) {
					PreparedStatement p = c.prepareStatement("select * from rcb") ;
					ResultSet r = p.executeQuery();
					int i = 1 ;
					while(r.next()) {
						f[i].setText(r.getString("pName"));
						f[i+1].setText("" + r.getDouble("bidPrice"));
						f[i+2].setText("" + r.getInt("rating"));
						f[i+3].setText( r.getString("Role"));
						i+=4 ;
					}
					
					for(int j=i ;j<=53;j+=4) {
						f[j].setText("");
						f[j+1].setText("" );
						f[j+2].setText("");
						f[j+3].setText("");
					}
					
					ResultSet r2 ;
					PreparedStatement p2 = c.prepareStatement("select avg(rating) from rcb") ;
					r2 = p2.executeQuery() ;
					Double ans = 0.0 ;
					while(r2.next()) {
						ans = r2.getDouble("avg(rating)") ;
					}
					String s = Double.toString(ans); 
					f[57].setText(s);
				}
				
				if(team.equals("kkr")) {
					PreparedStatement p = c.prepareStatement("select * from kkr") ;
					ResultSet r = p.executeQuery();
					int i = 1 ;
					while(r.next()) {
						f[i].setText(r.getString("pName"));
						f[i+1].setText("" + r.getDouble("bidPrice"));
						f[i+2].setText("" + r.getInt("rating"));
						f[i+3].setText( r.getString("Role"));
						i+=4 ;
					}
					for(int j=i ;j<=53;j+=4) {
						f[j].setText("");
						f[j+1].setText("" );
						f[j+2].setText("");
						f[j+3].setText("");
					}
					
					ResultSet r2 ;
					PreparedStatement p2 = c.prepareStatement("select avg(rating) from kkr") ;
					r2 = p2.executeQuery() ;
					Double ans = 0.0 ;
					while(r2.next()) {
						ans = r2.getDouble("avg(rating)") ;
					}
					String s = Double.toString(ans); 
					f[57].setText(s);
				}
				
				if(team.equals("csk")) {
					PreparedStatement p = c.prepareStatement("select * from csk") ;
					ResultSet r = p.executeQuery();
					int i = 1 ;
					while(r.next()) {
						f[i].setText(r.getString("pName"));
						f[i+1].setText("" + r.getDouble("bidPrice"));
						f[i+2].setText("" + r.getInt("rating"));
						f[i+3].setText( r.getString("Role"));
						i+=4 ;
					}
					for(int j=i ;j<=53;j+=4) {
						f[j].setText("");
						f[j+1].setText("" );
						f[j+2].setText("");
						f[j+3].setText("");
					}
					
					ResultSet r2 ;
					PreparedStatement p2 = c.prepareStatement("select avg(rating) from csk") ;
					r2 = p2.executeQuery() ;
					Double ans = 0.0 ;
					while(r2.next()) {
						ans = r2.getDouble("avg(rating)") ;
					}
					String s = Double.toString(ans); 
					f[57].setText(s);
				}
				
				if(team.equals("dc")) {
					PreparedStatement p = c.prepareStatement("select * from dc") ;
					ResultSet r = p.executeQuery();
					int i = 1 ;
					while(r.next()) {
						f[i].setText(r.getString("pName"));
						f[i+1].setText("" + r.getDouble("bidPrice"));
						f[i+2].setText("" + r.getInt("rating"));
						f[i+3].setText( r.getString("Role"));
						i+=4 ;
					}
					for(int j=i ;j<=53;j+=4) {
						f[j].setText("");
						f[j+1].setText("" );
						f[j+2].setText("");
						f[j+3].setText("");
					}
					
					ResultSet r2 ;
					PreparedStatement p2 = c.prepareStatement("select avg(rating) from dc") ;
					r2 = p2.executeQuery() ;
					Double ans = 0.0 ;
					while(r2.next()) {
						ans = r2.getDouble("avg(rating)") ;
					}
					String s = Double.toString(ans); 
					f[57].setText(s);
				}
				
				if(team.equals("gt")) {
					PreparedStatement p = c.prepareStatement("select * from gt") ;
					ResultSet r = p.executeQuery();
					int i = 1 ;
					while(r.next()) {
						f[i].setText(r.getString("pName"));
						f[i+1].setText("" + r.getDouble("bidPrice"));
						f[i+2].setText("" + r.getInt("rating"));
						f[i+3].setText( r.getString("Role"));
						i+=4 ;
					}
					for(int j=i ;j<=53;j+=4) {
						f[j].setText("");
						f[j+1].setText("" );
						f[j+2].setText("");
						f[j+3].setText("");
					}
					
					ResultSet r2 ;
					PreparedStatement p2 = c.prepareStatement("select avg(rating) from gt") ;
					r2 = p2.executeQuery() ;
					Double ans = 0.0 ;
					while(r2.next()) {
						ans = r2.getDouble("avg(rating)") ;
					}
					String s = Double.toString(ans); 
					f[57].setText(s);
				}
				
				if(team.equals("lsg")) {
					PreparedStatement p = c.prepareStatement("select * from lsg") ;
					ResultSet r = p.executeQuery();
					int i = 1 ;
					while(r.next()) {
						f[i].setText(r.getString("pName"));
						f[i+1].setText("" + r.getDouble("bidPrice"));
						f[i+2].setText("" + r.getInt("rating"));
						f[i+3].setText( r.getString("Role"));
						i+=4 ;
					}
					for(int j=i ;j<=53;j+=4) {
						f[j].setText("");
						f[j+1].setText("" );
						f[j+2].setText("");
						f[j+3].setText("");
					}
					
					ResultSet r2 ;
					PreparedStatement p2 = c.prepareStatement("select avg(rating) from lsg") ;
					r2 = p2.executeQuery() ;
					Double ans = 0.0 ;
					while(r2.next()) {
						ans = r2.getDouble("avg(rating)") ;
					}
					String s = Double.toString(ans); 
					f[57].setText(s);
				}
				
				if(team.equals("mi")) {
					PreparedStatement p = c.prepareStatement("select * from mi") ;
					ResultSet r = p.executeQuery();
					int i = 1 ;
					while(r.next()) {
						f[i].setText(r.getString("pName"));
						f[i+1].setText("" + r.getDouble("bidPrice"));
						f[i+2].setText("" + r.getInt("rating"));
						f[i+3].setText( r.getString("Role"));
						i+=4 ;
					}
					for(int j=i ;j<=53;j+=4) {
						f[j].setText("");
						f[j+1].setText("" );
						f[j+2].setText("");
						f[j+3].setText("");
					}
					
					ResultSet r2 ;
					PreparedStatement p2 = c.prepareStatement("select avg(rating) from mi") ;
					r2 = p2.executeQuery() ;
					Double ans = 0.0 ;
					while(r2.next()) {
						ans = r2.getDouble("avg(rating)") ;
					}
					String s = Double.toString(ans); 
					f[57].setText(s);
				}
				
				if(team.equals("pbks")) {
					PreparedStatement p = c.prepareStatement("select * from pbks") ;
					ResultSet r = p.executeQuery();
					int i = 1 ;
					while(r.next()) {
						f[i].setText(r.getString("pName"));
						f[i+1].setText("" + r.getDouble("bidPrice"));
						f[i+2].setText("" + r.getInt("rating"));
						f[i+3].setText( r.getString("Role"));
						i+=4 ;
					}
					for(int j=i ;j<=53;j+=4) {
						f[j].setText("");
						f[j+1].setText("" );
						f[j+2].setText("");
						f[j+3].setText("");
					}
					
					ResultSet r2 ;
					PreparedStatement p2 = c.prepareStatement("select avg(rating) from pbks") ;
					r2 = p2.executeQuery() ;
					Double ans = 0.0 ;
					while(r2.next()) {
						ans = r2.getDouble("avg(rating)") ;
					}
					String s = Double.toString(ans); 
					f[57].setText(s);
				}
				
				if(team.equals("rr")) {
					PreparedStatement p = c.prepareStatement("select * from rr") ;
					ResultSet r = p.executeQuery();
					int i = 1 ;
					while(r.next()) {
						f[i].setText(r.getString("pName"));
						f[i+1].setText("" + r.getDouble("bidPrice"));
						f[i+2].setText("" + r.getInt("rating"));
						f[i+3].setText( r.getString("Role"));
						i+=4 ;
					}
					for(int j=i ;j<=53;j+=4) {
						f[j].setText("");
						f[j+1].setText("" );
						f[j+2].setText("");
						f[j+3].setText("");
					}
					
					ResultSet r2 ;
					PreparedStatement p2 = c.prepareStatement("select avg(rating) from rr") ;
					r2 = p2.executeQuery() ;
					Double ans = 0.0 ;
					while(r2.next()) {
						ans = r2.getDouble("avg(rating)") ;
					}
					String s = Double.toString(ans); 
					f[57].setText(s);
				}
				
				if(team.equals("srh")) {
					PreparedStatement p = c.prepareStatement("select * from srh") ;
					ResultSet r = p.executeQuery();
					int i = 1 ;
					while(r.next()) {
						f[i].setText(r.getString("pName"));
						f[i+1].setText("" + r.getDouble("bidPrice"));
						f[i+2].setText("" + r.getInt("rating"));
						f[i+3].setText( r.getString("Role"));
						i+=4 ;
					}
					for(int j=i ;j<=53;j+=4) {
						f[j].setText("");
						f[j+1].setText("" );
						f[j+2].setText("");
						f[j+3].setText("");
					}
					
					ResultSet r2 ;
					PreparedStatement p2 = c.prepareStatement("select avg(rating) from srh") ;
					r2 = p2.executeQuery() ;
					Double ans = 0.0 ;
					while(r2.next()) {
						ans = r2.getDouble("avg(rating)") ;
					}
					String s = Double.toString(ans); 
					f[57].setText(s);
				}
    		}
    		catch(Exception e2) {
    			System.out.println(e2) ;
    		}
  
    	}
    }
    
    Connection get_connection() throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection c = null ;
		try {
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/auction","HP","Lihkin*27405") ;
		}
		catch(Exception e) {
			System.out.println(e) ;
		}
		return c ;
	}
    
}
public class test {
	public static void main(String[] args) {
		new auction() ;
		new team() ;
		new team_standing() ; 
	}
}
	

