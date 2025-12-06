import java.awt.*; 
import java.awt.event.*; 
import java.sql.*;

public class FormHospital extends Frame implements ActionListener {
    TextField tfBuscaNome = new TextField(25);

    TextField tfNome = new TextField(25); 
    TextField tfSalario = new TextField(25); 
    TextField tfCargo = new TextField(25);

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
        painelCentral.setLayout(new GridLayout(3, 2, 10, 10));

        Label lblNome = new Label("Nome:"); 
        Label lblSalario = new Label("Salario:"); 
        Label lblCargo = new Label("Cargo:");

        lblNome.setFont(fontNegrito); 
        lblSalario.setFont(fontNegrito); 
        lblCargo.setFont(fontNegrito);

        painelCentral.add(lblNome); painelCentral.add(tfNome); 
        painelCentral.add(lblSalario); painelCentral.add(tfSalario); 
        painelCentral.add(lblCargo); painelCentral.add(tfCargo);

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
            

        }

        if (e.getSource() == btnIncluir) { 
                try{

                    String nome = tfNome.getText();
                    int idade = Interger.parseInt(tfIdade.getText());
                    float peso = Float.parseFloat(tfPeso.getText());
                    float altura = Float.parseFloat(tfAltura.getText());

                    conectar();

                    PreparedStatement pst = conn.prepareStatement(
                        "INSERT INTO CadastroPacientes (Nome, Idade, Peso, Altura) VALUES (?, ?, ?, ?)"
                    );

                    pst.setString(1, nome);
                    pst.setInt(2, idade);
                    pst.setFloat(3, peso);
                    pst.setFloat(4, altura);

                    pst.executeUpdate();

                    pst.close();
                    conn.close();

                    JOptionPane.showMessageDialog(this, "Paciente incluido com sucesso!");

                }catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Erro. Idade, peso e altura devem ser numericos!")
                }catch (Exception ex){
                    ex.printStackTrace();
                }
        }

        if (e.getSource() == btnLimpar) { 
            tfNome.setText("");
            tfIdade.setText("");
            tfpeso.setText("");
            tfAltura.setText("");
            tfBuscaNome.setText("");
        }

        if (e.getSource() == btnApresentar) {

        }

        if (e.getSource() == btnCreditos) { 
            JOptionPane.showMessageDialog(
                this,
                "Trabalho desenvolvido por Lucas da Silva Santos - CB3030598 e Kaueh Farias Ferreira dos Santos - CB3031438.",
                "Créditos",
                JOptionPane.INFORMATION_MESSAGE
            );
        }

        if (e.getSource() == btnSair) { 
            System.exit(0);
        } 
    } 
}