import java.awt.*; 
import java.awt.event.*;
import java.sql.*;

import javax.swing.JOptionPane;

public class FormHospital extends Frame implements ActionListener {
    
TextField tfBuscaNome = new TextField(25);

    TextField tfNome = new TextField(25); 
    TextField tfIdade = new TextField(25); 
    TextField tfPeso = new TextField(25);
    TextField tfAltura = new TextField(25);

    Button btnPesquisar = new Button("Pesquisar"); 
    Button btnIncluir= new Button("Incluir"); 
    Button btnLimpar = new Button("Limpar"); 
    Button btnApresentar = new Button("Apresentar"); 
    Button btnCreditos = new Button("Creditos"); 
    Button btnSair = new Button("Sair");
    
    public FormHospital() {

        setTitle("Trabalho pratico Substitui P2"); 
        setSize(500, 260); 
        setLocation(300, 200); 
        setBackground(new Color(230, 230, 230)); 
        addWindowListener(new FechaJanela()); 
        setLayout(new BorderLayout(10, 10)); 
        Font fontNegrito = new Font("Dialog", Font.BOLD, 12);

        Panel painelSuperior = new Panel(new BorderLayout()); 
        painelSuperior.setBackground(getBackground());

        Panel painelBusca = new Panel(new FlowLayout(FlowLayout.CENTER, 14, 20)); 
        Label lblBuscaNome = new Label("Pesquisar por nome:"); 
        lblBuscaNome.setFont(fontNegrito);

        painelBusca.add(lblBuscaNome);
        painelBusca.add(tfBuscaNome); 
        painelBusca.add(btnPesquisar);

        painelSuperior.add(painelBusca, BorderLayout.NORTH);

        Panel painelCentral = new Panel(); 
        painelCentral.setBackground(getBackground()); 
        painelCentral.setLayout(new GridLayout(4, 2, 10, 4));

        Label lblNome = new Label("Nome:"); 
        Label lblIdade = new Label("Idade:"); 
        Label lblPeso = new Label("Peso:");
        Label lblAltura = new Label("Altura:");

        lblNome.setFont(fontNegrito); 
        lblIdade.setFont(fontNegrito); 
        lblPeso.setFont(fontNegrito);
        lblAltura.setFont(fontNegrito);

        painelCentral.add(lblNome); painelCentral.add(tfNome); 
        painelCentral.add(lblIdade); painelCentral.add(tfIdade); 
        painelCentral.add(lblPeso); painelCentral.add(tfPeso);
        painelCentral.add(lblAltura); painelCentral.add(tfAltura);

        Panel painelInferior = new Panel(new GridLayout(1, 5)); 
        painelInferior.setBackground(Color.lightGray);

        painelInferior.add(btnIncluir); 
        painelInferior.add(btnLimpar); 
        painelInferior.add(btnApresentar); 
        painelInferior.add(btnCreditos); 
        painelInferior.add(btnSair);

        add(painelSuperior, BorderLayout.NORTH); 
        add(painelCentral, BorderLayout.CENTER); 
        add(painelInferior, BorderLayout.SOUTH);

        btnPesquisar.addActionListener(this); 
        btnIncluir.addActionListener(this); 
        btnLimpar.addActionListener(this); 
        btnApresentar.addActionListener(this); 
        btnCreditos.addActionListener(this); 
        btnSair.addActionListener(this);

    }

    private Connection conectar() throws Exception{
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection("jdbc:sqlite:Hospital.db");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPesquisar) {
            
            try{

                if (tfBuscaNome.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Digite um nome para pesquisar!");
                    return;
                }

                String nome = tfBuscaNome.getText().trim();
                String dados = "";
                Connection con = conectar();

                PreparedStatement st = con.prepareStatement(
                    "SELECT * FROM CadastroPacientes WHERE Nome LIKE ?"
                );
                
                st.setString(1, "%" + nome + "%");

                ResultSet rs = st.executeQuery();

                if (rs.next()) {

                    dados = 
                    "ID: " + rs.getInt("ID") +
                    "\nNome: " + rs.getString("Nome") +
                    "\nIdade: " + rs.getInt("Idade") +
                    "\nPeso: " + rs.getFloat("Peso") +
                    "\nAltura: " + rs.getFloat("Altura");
                    
                    JOptionPane.showMessageDialog(null, dados, "Paciente Encontrado", JOptionPane.INFORMATION_MESSAGE);
                
                }else {
                    
                    JOptionPane.showMessageDialog(null, "Nenhum paciente encontrado.");
                
                } 

                rs.close();
                st.close();
                con.close();

            } catch (Exception ex){
                    ex.printStackTrace();
            }

        }

        if (e.getSource() == btnIncluir) { 
            try{

                if (tfNome.getText().trim().isEmpty() ||
                    tfIdade.getText().trim().isEmpty() ||
                    tfPeso.getText().trim().isEmpty() ||
                    tfAltura.getText().trim().isEmpty()) {
                    
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                    return;
                }

                String nome = tfNome.getText();
                int idade = Integer.parseInt(tfIdade.getText());
                float peso = Float.parseFloat(tfPeso.getText());
                float altura = Float.parseFloat(tfAltura.getText());

                Connection con = conectar();

                PreparedStatement pst = con.prepareStatement(
                    "INSERT INTO CadastroPacientes (Nome, Idade, Peso, Altura) VALUES (?, ?, ?, ?)"
                );

                pst.setString(1, nome);
                pst.setInt(2, idade);
                pst.setFloat(3, peso);
                pst.setFloat(4, altura);

                pst.executeUpdate();

                pst.close();
                con.close();

                JOptionPane.showMessageDialog(null, "Paciente incluido com sucesso!");

            }catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Erro. Idade, peso e altura devem ser numericos!");
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        if (e.getSource() == btnLimpar) { 
            tfNome.setText("");
            tfIdade.setText("");
            tfPeso.setText("");
            tfAltura.setText("");
            tfBuscaNome.setText("");
        }

        if (e.getSource() == btnApresentar) {
            try {
            
                Connection con = conectar();
                PreparedStatement pst = con.prepareStatement(
                    "SELECT * FROM CadastroPacientes"
                );
                
                ResultSet rs = pst.executeQuery();
                
                String dados = "";
                boolean temRegistros = false;
                
                while (rs.next()) {
                    temRegistros = true;
                    
                        dados += 
                            "ID: " + rs.getInt("ID") +
                            "\nNome: " + rs.getString("Nome") +
                            "\nIdade: " + rs.getInt("Idade") +
                            "\nPeso: " + rs.getFloat("Peso") +
                            "\nAltura: " + rs.getFloat("Altura") +
                            "\n-----------------------------\n";
                }
                
                if (!temRegistros) {
                    JOptionPane.showMessageDialog(null, "Nenhum paciente cadastrado.");
                } else {
                    JOptionPane.showMessageDialog(null, dados, "Todos os Pacientes",
                    JOptionPane.INFORMATION_MESSAGE);
                }
                
                    rs.close();
                    pst.close();
                    con.close();
                
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                    }
                
                    if (e.getSource() == btnCreditos) { 
                        JOptionPane.showMessageDialog(null,
                "Trabalho desenvolvido por Lucas da Silva Santos - CB3030598 e Kaueh Farias Ferreira dos Santos - CB3031438.",
                  "Creditos",
                        JOptionPane.INFORMATION_MESSAGE
            );
        }

        if (e.getSource() == btnSair) { 
            System.exit(0);
        } 
    } 
}