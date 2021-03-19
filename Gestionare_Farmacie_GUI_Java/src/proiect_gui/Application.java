package proiect_gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.*;

import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextField;

public class Application extends JFrame{

	List<Farmacie> farmacii = new ArrayList<>();
	List<Farmacie> nfr = new ArrayList<>();
	private static final long serialVersionUID = 1L;
	//private final static String newline = "\n";
	Dimension size;
	JPanel t1, t2, t3, t4, t5, tamount, p1, p2, p3, shop1,shop2,shop3,shop4,shop5;
	JLabel namef, locf, medname, price, number, medamount,search, namei, amounti, pricei,tipsi, totaltopay;
	JTextField nameft, locft, mednamet, numbert, searcht;
	JCheckBox meda1 = new JCheckBox("ANALGEZIC", true), meda2 = new JCheckBox("PROBIOTIC", true), meda3 = new JCheckBox("ANTIOXIDANT", true),
			meda4 = new JCheckBox("NARCOTIC", true), meda5 = new JCheckBox("ANTIINFECTIOS", true), meda6 = new JCheckBox("ANTIINFLAMATOARE", true);
	JButton addbtn = new JButton("Add Farmacy"), addbtn2 = new JButton("Add Medicament"), findbtn = new JButton("Find"), buybtn = new JButton("Buy"),
			paybtn = new JButton("Pay"), topay = new JButton("Proceed to Payment"), resetbtn = new JButton("Reset");
	JFormattedTextField pricet;
	JSpinner amount;
	JList<String> list, listr, listf = new JList<String>(find_meds("")), listp3 = new JList<String>();
	JScrollPane listScrollPaneF, listScrollPane, listScrollPaner, listScrollPaneP3;
	DefaultListModel<String> dfs, hardcopy;
	HashMap<Integer, DefaultListModel<String>> meds = new HashMap<>();
	int med_to_buy, to, poz, pm;
	String last_searched = "", value;

	Payment payment = new Payment();
	
	public Application() {}
	
	public Application(File file) throws IOException{
		if(!file.exists()) {
			file.createNewFile();
		}
		
		meda1.setHorizontalAlignment(SwingConstants.CENTER);
		meda2.setHorizontalAlignment(SwingConstants.CENTER);
		meda3.setHorizontalAlignment(SwingConstants.CENTER);
		meda4.setHorizontalAlignment(SwingConstants.CENTER);
		meda5.setHorizontalAlignment(SwingConstants.CENTER);
		meda6.setHorizontalAlignment(SwingConstants.CENTER);
		
		deserialize(file);
		nfr = copy(farmacii);
		setLayout(new BorderLayout());
		setSize(1000,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		
		size = getBounds().getSize();
	
	    //Create the list and put it in a scroll pane.
	    list = new JList<String>(add_elements());
	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list.setSelectedIndex(0);
	    list.setVisibleRowCount(100);
	    JScrollPane listScrollPane = new JScrollPane(list);
	    listScrollPane.setPreferredSize(new Dimension((int) (size.getWidth() * 0.15), (int) (size.getHeight() * 0.92)));
	    listScrollPane.setBackground(Color.LIGHT_GRAY);
	    add(listScrollPane, BorderLayout.WEST);

	    //Create the list and put it in a scroll pane.
	    if(farmacii.size() > 0) {
	    	listr = new JList<String>(add_meds(farmacii.get(0)));
	    }
	    else {
	    	listr = new JList<String>(new DefaultListModel<String>());
	    }
	    listr.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    listr.setSelectedIndex(0);
	    listr.setVisibleRowCount(100);
	    JScrollPane listScrollPaner = new JScrollPane(listr);
	    listScrollPaner.setPreferredSize(new Dimension((int) (size.getWidth() * 0.15), (int) (size.getHeight() * 0.92)));
	    listScrollPaner.setBackground(Color.LIGHT_GRAY);
	    add(listScrollPaner, BorderLayout.EAST);
	    JLabel tl = new JLabel();
	    if(farmacii.size() > 0) {
	    	tl.setText(farmacii.get(list.getSelectedIndex()).getNume() + " " + farmacii.get(list.getSelectedIndex()).getLoc());
	    }
	    else {
	    	tl.setText("");
	    }
	    tl.setHorizontalAlignment(JLabel.CENTER);
	    tl.setBackground(Color.GRAY);
	    tl.setFont(new Font("Arial", 1, 24));
	    add(tl, BorderLayout.NORTH);

	    JPanel main = new JPanel();
	    setup_main(main);
	    add(main, BorderLayout.CENTER);
	    
	    JPanel bottom = new JPanel(new BorderLayout());
	    JButton removefarm = new JButton("Remove Farmacy");
	    removefarm.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(farmacii.size() != 0) {
					if(list.getSelectedIndex() != -1) {
						farmacii.remove(list.getSelectedIndex());
						list.setModel(add_elements());
						list.setSelectedIndex(0);
						if(farmacii.size() == 0) {
							list.setSelectedIndex(-1);
						}
					}
				}		
			}
	    });
	  
	    JButton farm = new JButton("Farmacy");
	    
	    farm.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				dfs = new DefaultListModel<String>();
				setup_main(main);
				revalidate();
				repaint();
			}
	    });
	    
		addbtn.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!nameft.getText().equals("")) {
					if(!locft.getText().equals("")) {
						String fname = nameft.getText();
						String floc = locft.getText();
						List<tip> ftipa = get_accepted_meds(t4);
						farmacii.add(new Farmacie(fname, floc, ftipa));
						list.setModel(add_elements());
						list.setSelectedIndex(farmacii.size() - 1);
					    nfr = copy(farmacii);
					}
				}
			}
		});
		
		addbtn2.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(farmacii.size() != 0) {
					Farmacie f = farmacii.get(list.getSelectedIndex());
					String name = mednamet.getText();
					if(!name.equals("")) {
						String p = pricet.getText().replaceAll(",", "");
						double price = Double.parseDouble(p);
						int nr = (int) amount.getValue();
						List<tip> tips = get_accepted_meds(t4);
						int inf = f.find_poz(name);
						if(inf != -1) {
							f.getStoc().get(inf).addAmount(nr);
						}
						else {
							Medicament med = new Medicament(name, price, tips, nr);
							boolean ok = gettips(f.getTip(), tips);
							if(ok) {
								f.getStoc().add(med);								
							}
							listr.setModel(add_meds(f));
						    listr.setSelectedIndex(f.getStoc().size() > 0 ? f.getStoc().size() - 1 : -1);
						}
						nfr = copy(farmacii);
						pricet.setText("");
						mednamet.setText("");
						amount.setValue(50);
					}
				}
			}
		});

		JButton med = new JButton("Medicament");
	    med.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				dfs = new DefaultListModel<String>();
				setup_main2(main);
				revalidate();
				repaint();
			}
	    });
	    
	    JButton removemed = new JButton("Remove Medicament");
	    removemed.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int pozf = list.getSelectedIndex();
				int pozm = listr.getSelectedIndex();
				if(pozf != -1) {
					if(pozm != -1) {
						farmacii.get(pozf).getStoc().remove(pozm);
						listr.setModel(add_meds(farmacii.get(pozf)));
					    listr.setSelectedIndex(farmacii.get(pozf).getStoc().size() > 0 ? 0 : -1);
						nfr = copy(farmacii);
					}
				}
			}
	    	
	    });
	    
	    JButton shop = new JButton("Shop");
	    shop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dfs = new DefaultListModel<String>();
				setup_main3(main, last_searched, payment.total);
				revalidate();
			}
	    });
	    
	    JPanel bleft = new JPanel();
	    bleft.add(farm);
	    bleft.add(med);
	    JPanel bright = new JPanel();
	    bright.add(removefarm);
	    bright.add(removemed);
	    JPanel bmid = new JPanel();
	    bmid.add(shop);
	    
	    bottom.add(bleft, BorderLayout.WEST);
	    bottom.add(bright, BorderLayout.EAST);
	    bottom.add(bmid, BorderLayout.CENTER);
	    add(bottom, BorderLayout.SOUTH);
	    
	    list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(list.getSelectedIndex() != -1) {					
					Farmacie f = farmacii.get(list.getSelectedIndex());
					tl.setText(f.getNume() + " " + f.getLoc());
					listr.setModel(add_meds(f));
					listr.setSelectedIndex(-1);
				    if(f.getStoc().size() != 0) {
				    	listr.setSelectedIndex(0);
				    }
				}
			}
	    });

	    
	    findbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(!searcht.getText().trim().equals("")) {
					last_searched = searcht.getText().trim();
					dfs = find_meds(last_searched);
					listf.setModel(dfs);
					listf.setSelectedIndex(dfs.size() > 0 ? 0 : -1);
				}
			}
	    	
	    });
	    
	    buybtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Medicament m = nfr.get(poz).getStoc().get(pm);
				m.minusAmount(1);
				payment.total += m.getPret();
				payment.add(poz, pm, 1);
				//int x = m.getAmount() - payment.mp.get(poz).get(pm);
				if(m.getAmount() <= 0) {
					nfr.get(poz).getStoc().remove(pm);
					listr.setModel(add_meds(nfr.get(poz)));
					DefaultListModel<String> s = copy(meds.get(poz));
					int torem = -1;
					for(int i = 0; i < s.size(); i++) {
						String n = s.get(i);
						//System.out.println("Searching in " + n + " for " + n.substring(n.indexOf("-") + 1).trim());
						if(n.substring(n.indexOf("-") + 1).trim().equals(m.getNume())) {
							torem = i;
							break;
						}
					}
					if(torem != -1) {
						System.out.println("There is med to remove");
						meds.get(poz).remove(torem);
					}
					else {
						System.out.println("There is NO med to remove");
					}
					if(meds.get(poz).size() == 0) {
						System.out.println("There are NO meds left");
						meds.remove(poz);
						listp3.removeAll();
						if(meds.keySet().size() > 0) {
							String asd = String.valueOf(poz) + " - " + nfr.get(poz).getNume();
							for(int i = 0; i < dfs.size(); i++) {
								if((asd.equals(dfs.get(i)))) {
									dfs.remove(i);
									break;
								}
							}
							System.out.println("\n" + dfs + "\n");
							System.out.println("There are farmacies left");
							listf.setModel(dfs);
							listf.setSelectedIndex(0);
						}
						else {
							System.out.println("There are NO farmacies left");
							setup_main3(main, "", payment.total);
						}
					}
					else {
						System.out.println("There are MEDICAMENTS left");
						listp3.setModel(meds.get(poz));
					}
				}
				else {
					amounti.setText("In Stoc: " + m.getAmount());
				}
				totaltopay.setText("Total: " + String.valueOf(payment.total));
				revalidate();
			}
	    	
	    });

	    topay.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				setup_main4(main);
				revalidate();
			}
	    });
	    
	    paybtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*System.out.println(payment.mp);
				for(Integer i : payment.mp.keySet()) {
					HashMap<Integer, Integer> hmp = payment.mp.get(i);
					Farmacie f = farmacii.get(i);
					for(Integer j : hmp.keySet()) {
						Medicament m = f.getStoc().get(j);
						
					}
				}*/
				farmacii = copy(nfr);
				payment = new Payment();
				last_searched = "";
				setup_main5(main);
				revalidate();
			}
	    });
	    
	    resetbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nfr = copy(farmacii);
				payment = new Payment();
				setup_main3(main, "", 0.0);
				revalidate();
			}
	    });	   
	    
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				serialize(file);
			}
		});
	}
	
	
	private void deserialize(File farmacie) {
		try {
			FileInputStream fis = new FileInputStream(farmacie);
			ObjectInputStream ois = new ObjectInputStream(fis);
			List<?> m = (List <?>) ois.readObject();
			for(Object o : m) {
				farmacii.add((Farmacie) o);
			}
			ois.close();
			fis.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}

	private void serialize(File farmacie) {
		try {
			FileOutputStream fos = new FileOutputStream(farmacie);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(farmacii);
			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<tip> get_accepted_meds(JPanel t4){
		List<tip> tips = new ArrayList<>();
		for(Component c : t4.getComponents()) {
			JCheckBox nc = (JCheckBox)c;
			if(nc.isSelected()) {
				tips.add(tip.valueOf(nc.getText()));
			}
		}
		return tips;
	}
	
	private DefaultListModel<String> add_elements() {
		DefaultListModel<String> md = new DefaultListModel<String>();
		for(Farmacie f : farmacii) {
			md.addElement(f.getNume());
		}
		return md;
	}
	
	private DefaultListModel<String> add_meds(Farmacie f) {
		DefaultListModel<String> md = new DefaultListModel<String>();
		List<Medicament> meds = f.getStoc();
		for(Medicament m : meds) {
			md.addElement(m.getNume());
		}
		return md;
	}
	
	private void setup_main5(JPanel main) {
		main.removeAll();
		JPanel njp = new JPanel();
		njp.setLayout(new BorderLayout());
		JLabel lbl = new JLabel("Thank you for your purchase!");
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setVerticalAlignment(SwingConstants.CENTER);
		njp.add(lbl, BorderLayout.CENTER);
	    main.setBorder(new EmptyBorder(200, 200, 200, 200));
	    main.add(njp);
	}
	
	private void setup_main4(JPanel main) {
		main.removeAll();
		if(payment.mp.keySet().size() > 0) {			
			JPanel njp = new JPanel();
			njp.setLayout(new BoxLayout(njp, BoxLayout.PAGE_AXIS));
			double totalprice = 0.0;
			for(Integer farm : payment.mp.keySet()) {
				Farmacie f = farmacii.get(farm);
				JPanel np = new JPanel(new FlowLayout(FlowLayout.LEFT));
				np.add(new JLabel(f.getNume() + " - " + f.getLoc() + " : "));
				HashMap<Integer, Integer> meds = payment.mp.get(farm);
				for(Integer med : meds.keySet()) {
					Medicament m = f.getStoc().get(med);
					totalprice += m.getPret() * meds.get(med);
					np.add(new JLabel(m.getNume() + ", Amount: " + meds.get(med) + ", Total: " + String.valueOf(m.getPret() * meds.get(med)) + " | "));
				}
				njp.add(np);
			}
			JPanel lastPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			lastPanel.add(new JLabel("Total payment: " + String.valueOf(totalprice)));
			lastPanel.add(paybtn);
			lastPanel.add(resetbtn);
			njp.add(lastPanel);
			main.add(njp);
		}
	}
	
	private void setup_main3(JPanel main, String ls, double total) {
		main.removeAll();
		main.setBackground(Color.DARK_GRAY);
	    main.setLayout(new BorderLayout());
	    main.setBorder(new EmptyBorder(60, 20, 60, 20));
	    p1 = new JPanel();
	    p1.setLayout(new BoxLayout(p1, BoxLayout.PAGE_AXIS));
	    t1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    p1.setPreferredSize(new Dimension(400,368)); // 120, 368
	    search = new JLabel("Search: ");
	    search.setPreferredSize(new Dimension(50,25));
	    search.setHorizontalAlignment(SwingConstants.RIGHT);
	    searcht = new JTextField(12);
	    searcht.setText(ls);
	    t1.add(search);
	    t1.add(searcht);
	    t1.add(findbtn);
	    JLabel types = new JLabel("- Medicament info -");
		shop1 = new JPanel(); shop1.add(types);
		namei = new JLabel("Name: ");
		shop2 = new JPanel(); shop2.add(namei);
		pricei = new JLabel("Price: ");
		shop3 = new JPanel(); shop3.add(pricei);
		amounti = new JLabel("In Stoc: ");
		shop4 = new JPanel(); shop4.add(amounti);
		totaltopay = new JLabel("Total: " + String.valueOf(total));
		shop5 = new JPanel(); shop5.add(buybtn); shop5.add(totaltopay); shop5.add(topay);
		p1.add(t1);
	    p1.add(shop1);
	    p1.add(shop2);
	    p1.add(shop3);
	    p1.add(shop4);
		t4.removeAll();
		t4.setLayout(new FlowLayout(FlowLayout.LEFT));
		tipsi = new JLabel("Tips: ");
		t4.add(tipsi);
		p1.add(t4);
		p1.add(shop5);
		
	    p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    listf = new JList<String>(find_meds(ls));
	    listf.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    listf.setSelectedIndex(-1);
	    listf.setVisibleRowCount(20);
	    listScrollPaneF = new JScrollPane(listf);
	    listScrollPaneF.setPreferredSize(new Dimension(100,368));
	    listScrollPaneF.setBackground(Color.LIGHT_GRAY);

		p3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		listp3 = new JList<String>(new DefaultListModel<String>());
	    listp3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    listp3.setSelectedIndex(-1);
	    listp3.setVisibleRowCount(20);
	    listScrollPaneP3 = new JScrollPane(listp3);
	    listScrollPaneP3.setPreferredSize(new Dimension(120,368));
	    listScrollPaneP3.setBackground(Color.LIGHT_GRAY);
	    p3.add(listScrollPaneP3);
	    
	    p2.add(listScrollPaneF);
	    main.add(p1, BorderLayout.WEST);
	    main.add(p2, BorderLayout.EAST);
	    main.add(p3, BorderLayout.CENTER);
	    listf.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(listf.getSelectedIndex() != -1) {
					value = listf.getSelectedValue().trim();
					to = value.indexOf('-');
					//System.out.println("value = " + value);
					//System.out.println("to = " + to);
					poz = Integer.parseInt(value.substring(0, to).trim());
					//System.out.println("poz = " + poz);
					listp3.setModel(meds.get(poz));
					listp3.setSelectedIndex(0);
					revalidate();
				}
			}
	    });
	    
	    listp3.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(listp3.getSelectedIndex() != -1) {					
					Farmacie f = nfr.get(poz);
					String n = listp3.getSelectedValue().trim();
					pm = Integer.parseInt(n.substring(0, n.indexOf('-')).trim());
					if(f.getStoc().size() > 0) {
						Medicament m = f.getStoc().get(pm);
						namei.setText("Name: " + m.getNume());
						pricei.setText("Price: " + String.valueOf(m.getPret()));
						amounti.setText("In Stoc: " + String.valueOf(m.getAmount()));
						//System.out.println(m.getTip());
						set_checkboxes(t4, m.getTip());
					}
					revalidate();
				}
			}
	    });
	
	    findbtn.doClick();
	}
	
	private void setup_main2(JPanel main) {
		main.removeAll();
		main.setBackground(Color.DARK_GRAY);
	    main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
	    main.setBorder(new EmptyBorder(100, 100, 100, 100));
	    t1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		medname = new JLabel("Medicament name: ");
		medname.setPreferredSize(new Dimension(200,25));
		medname.setHorizontalAlignment(SwingConstants.RIGHT);
		mednamet = new JTextField(20);
		t1.add(medname);
		t1.add(mednamet);
		
	    t2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		price = new JLabel("Medicament price: ");
		price.setPreferredSize(new Dimension(200,25));
		price.setHorizontalAlignment(SwingConstants.RIGHT);
		
		pricet = new JFormattedTextField();
		pricet.setPreferredSize(new Dimension(100,25));
		pricet.setFormatterFactory(new AbstractFormatterFactory () {
			@Override
			public AbstractFormatter getFormatter(JFormattedTextField tf) {			
	            NumberFormat format = DecimalFormat.getInstance();
	            format.setMinimumFractionDigits(2);
	            format.setMaximumFractionDigits(2);
	            format.setRoundingMode(RoundingMode.HALF_UP);
	            InternationalFormatter formatter = new InternationalFormatter(format);
	            formatter.setAllowsInvalid(false);
	            formatter.setMinimum(0.0);
	            formatter.setMaximum(Double.MAX_VALUE);
	            return formatter;
			}
		});
		t2.add(price);
		t2.add(pricet);
		
		tamount = new JPanel(new FlowLayout(FlowLayout.CENTER));
		amount = new JSpinner(new SpinnerNumberModel(50,0,200,1));
		amount.setPreferredSize(new Dimension(100,25));
		medamount = new JLabel("Medication amount: ");
		medamount.setPreferredSize(new Dimension(200, 25));
		medamount.setHorizontalAlignment(SwingConstants.RIGHT);
		tamount.add(medamount);
		tamount.add(amount);

		t4 = new JPanel(new GridLayout(2,3));
		add_checkboxes(t4);

		t3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel types = new JLabel("--------------- Medicament type ---------------");
		t3.add(types);
		main.add(t1);
		main.add(t2);
		main.add(tamount);
		main.add(t3);
		main.add(t4);
		
		t5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		t5.add(addbtn2);
		main.add(t5);
		main.setVisible(true);
	}
	
	private void setup_main(JPanel main) {
		main.removeAll();
		main.setBackground(Color.DARK_GRAY);
	    main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
	    main.setBorder(new EmptyBorder(100, 100, 100, 100));
	    t1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		namef = new JLabel("Farmacy name: ");
		namef.setPreferredSize(new Dimension(100,25));
		namef.setHorizontalAlignment(SwingConstants.RIGHT);
		nameft = new JTextField(20);
		t1.add(namef);
		t1.add(nameft);
		
	    t2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		locf = new JLabel("Farmacy location: ");
		locf.setPreferredSize(new Dimension(100,25));
		locft = new JTextField(20);
		locf.setHorizontalAlignment(SwingConstants.RIGHT);
		t2.add(locf);
		t2.add(locft);

		t4 = new JPanel(new GridLayout(2,3));
		add_checkboxes(t4);

		t3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel types = new JLabel("--------------- Type of Medicaments accepted in this Farmacy ---------------");
		t3.add(types);
		main.add(t1);
		main.add(t2);
		main.add(t3);
		main.add(t4);
		
		t5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		t5.add(addbtn);
		main.add(t5);
		main.setVisible(true);
	}
	
	private void add_checkboxes(JPanel t4) {
		t4.add(meda1);
		t4.add(meda2);
		t4.add(meda3);
		t4.add(meda4);
		t4.add(meda5);
		t4.add(meda6);
	}
	
	private DefaultListModel<String> find_meds(String name){
		DefaultListModel<String> md = new DefaultListModel<String>();
		meds = new HashMap<>();
		if(!name.equals("")) {			
			for(int i = 0; i < farmacii.size(); i++) {
				List<Medicament> mds = farmacii.get(i).getStoc();
				boolean ok = false;
				for(int j = 0; j < mds.size(); j++) {
					if(mds.get(j).getNume().contains(name)) {
						if(!ok) {							
							md.addElement(String.valueOf(i) + " - " + farmacii.get(i).getNume());
							ok = true;
						}
						if(meds.containsKey(i)) {
							meds.get(i).addElement(String.valueOf(j) + " - " + mds.get(j).getNume());
						}
						else {
							DefaultListModel<String> a = new DefaultListModel<String>();
							a.addElement(String.valueOf(j) + " - " + mds.get(j).getNume());
							meds.put(i, a);
						}
					}
				}
			}
		}
		return md;
	}

	private void set_checkboxes(JPanel t4, List<tip> tips) {
		t4.removeAll();
		String s = "";
		for(tip t : tips) {
			s += t.name() + " ";
		}
		t4.add(new JLabel("Tips: " + s.trim()));
	}
	
	private DefaultListModel<String> copy(DefaultListModel<String> object){
		DefaultListModel<String> copy = new DefaultListModel<String>();
		for(int i = 0; i<object.size(); i++) {
			copy.addElement(new String(object.get(i)));
		}
		return copy;
	}
	
	private List<Farmacie> copy(List<Farmacie> object){
		List<Farmacie> copy = new ArrayList<>();
		for(Farmacie f : object){
			copy.add(Farmacie.copy(f));
		}
		return copy;
	}
	
	boolean gettips(List<tip> ft, List<tip> mt) {
		boolean ok = true;
		for(tip t : mt) {
			if(!ft.contains(t)) {
				ok = false;
				break;
			}
		}
		return ok;
	}
	
}
