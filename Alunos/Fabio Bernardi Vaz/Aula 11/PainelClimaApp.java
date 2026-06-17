package api.tempo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class PainelClimaApp {
    
    private final JFrame janelaPrincipal;
    private JTextField entradaCidade;
    private JButton acaoPesquisar;
    
    private JLabel lblCidade, lblTemperatura, lblCondicao;
    private JLabel cardExtremos, cardUmidade, cardChuva, cardVento;

    private final BuscadorApiTempo buscadorApi;

    private final Color FUNDO = new Color(20, 22, 30);
    private final Color FUNDO_CARDS = new Color(32, 35, 48);
    private final Color COR_FONTE_PRINCIPAL = new Color(230, 235, 240);
    private final Color COR_FONTE_SECUNDARIA = new Color(150, 160, 180);
    private final Color AZUL_BOTAO = new Color(37, 99, 235);

    public PainelClimaApp() {
        this.buscadorApi = new BuscadorApiTempo();
        this.janelaPrincipal = new JFrame("Previsão do Tempo");
        inicializarComponentes();
        configurarAcoes();
    }

    public void exibir() {
        janelaPrincipal.setVisible(true);
    }

    private void inicializarComponentes() {
        janelaPrincipal.setSize(550, 400);
        janelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaPrincipal.setLocationRelativeTo(null);
        janelaPrincipal.getContentPane().setBackground(FUNDO);
        janelaPrincipal.setLayout(new BorderLayout());

        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        painelTopo.setBackground(FUNDO);

        entradaCidade = new JTextField(20);
        entradaCidade.setFont(new Font("SansSerif", Font.PLAIN, 16));
        entradaCidade.setBackground(FUNDO_CARDS);
        entradaCidade.setForeground(COR_FONTE_PRINCIPAL);
        entradaCidade.setCaretColor(COR_FONTE_PRINCIPAL);
        entradaCidade.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 65, 80), 1, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        acaoPesquisar = new JButton("Buscar Tempo");
        acaoPesquisar.setFont(new Font("SansSerif", Font.BOLD, 14));
        acaoPesquisar.setBackground(AZUL_BOTAO);
        acaoPesquisar.setForeground(new Color(100, 170, 255));
        acaoPesquisar.setOpaque(true);
        acaoPesquisar.setFocusPainted(false);
        acaoPesquisar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        acaoPesquisar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        painelTopo.add(entradaCidade);
        painelTopo.add(acaoPesquisar);
        janelaPrincipal.add(painelTopo, BorderLayout.NORTH);

        JPanel painelCentral = new JPanel(new GridLayout(1, 2, 20, 0));
        painelCentral.setBackground(FUNDO);
        painelCentral.setBorder(new EmptyBorder(20, 25, 30, 25));

        JPanel blocoEsquerdo = new JPanel();
        blocoEsquerdo.setLayout(new BoxLayout(blocoEsquerdo, BoxLayout.Y_AXIS));
        blocoEsquerdo.setBackground(FUNDO);

        lblCidade = new JLabel("Cidade");
        lblCidade.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblCidade.setForeground(new Color(100, 170, 255));
        
        lblTemperatura = new JLabel("--°");
        lblTemperatura.setFont(new Font("SansSerif", Font.BOLD, 75));
        lblTemperatura.setForeground(COR_FONTE_PRINCIPAL);
        
        lblCondicao = new JLabel("Pronto para buscar...");
        lblCondicao.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblCondicao.setForeground(COR_FONTE_SECUNDARIA);

        blocoEsquerdo.add(Box.createVerticalStrut(20));
        blocoEsquerdo.add(lblCidade);
        blocoEsquerdo.add(Box.createVerticalStrut(10));
        blocoEsquerdo.add(lblTemperatura);
        blocoEsquerdo.add(Box.createVerticalStrut(5));
        blocoEsquerdo.add(lblCondicao);

        JPanel blocoDireito = new JPanel(new GridLayout(4, 1, 0, 10));
        blocoDireito.setBackground(FUNDO);

        cardExtremos = gerarCardInfo("Máx / Mín", "--");
        cardUmidade = gerarCardInfo("Umidade", "--");
        cardChuva = gerarCardInfo("Chuva", "--");
        cardVento = gerarCardInfo("Vento", "--");

        blocoDireito.add(cardExtremos);
        blocoDireito.add(cardUmidade);
        blocoDireito.add(cardChuva);
        blocoDireito.add(cardVento);

        painelCentral.add(blocoEsquerdo);
        painelCentral.add(blocoDireito);
        janelaPrincipal.add(painelCentral, BorderLayout.CENTER);
    }

    private JLabel gerarCardInfo(String titulo, String valor) {
        JLabel rotulo = new JLabel();
        rotulo.setOpaque(true);
        rotulo.setBackground(FUNDO_CARDS);
        rotulo.setBorder(new EmptyBorder(10, 15, 10, 15));
        rotulo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        alterarTextoCard(rotulo, titulo, valor);
        return rotulo;
    }

    private void alterarTextoCard(JLabel rotulo, String titulo, String valor) {
        rotulo.setText(String.format("<html><div style='width: 150px; display: flex; justify-content: space-between;'>" +
                "<span style='color: #96a0b4;'>%s: </span>" +
                "<strong style='color: #e6ebf0; font-size: 110%%;'>%s</strong>" +
                "</div></html>", titulo, valor));
    }

    private void configurarAcoes() {
        acaoPesquisar.addActionListener(e -> dispararBusca());
        entradaCidade.addActionListener(e -> dispararBusca()); 
    }

    private void dispararBusca() {
        String alvo = entradaCidade.getText().trim();
        if (alvo.isEmpty()) {
            JOptionPane.showMessageDialog(janelaPrincipal, "Digite o nome de uma cidade.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        prepararInterfaceCarregamento();

        new Thread(() -> {
            try {
                RegistroClima dados = buscadorApi.consultarTempo(alvo);
                
                SwingUtilities.invokeLater(() -> atualizarInterfaceSucesso(dados));

            } catch (Exception erro) {
                erro.printStackTrace();
                SwingUtilities.invokeLater(this::atualizarInterfaceErro);
            }
        }).start();
    }

    private void prepararInterfaceCarregamento() {
        acaoPesquisar.setEnabled(false);
        lblCidade.setText("Processando...");
        lblTemperatura.setText("...");
        lblCondicao.setText("Consultando base de dados...");
    }

    private void atualizarInterfaceSucesso(RegistroClima dados) {
        lblCidade.setText(dados.getLocalidade());
        lblTemperatura.setText(Math.round(dados.getTemperaturaMomento()) + "°");
        lblCondicao.setText(dados.getDescricaoTempo());

        alterarTextoCard(cardExtremos, "Máx/Mín", Math.round(dados.getTemperaturaMax()) + "° / " + Math.round(dados.getTemperaturaMin()) + "°");
        alterarTextoCard(cardUmidade, "Umidade", Math.round(dados.getPercentualUmidade()) + "%");
        alterarTextoCard(cardChuva, "Chuva", String.format("%.1f mm", dados.getVolumeChuva()));
        
        String infoVento = Math.round(dados.getVelocidadeVento()) + " km/h (" + dados.obterPontoCardealVento() + ")";
        alterarTextoCard(cardVento, "Vento", infoVento);

        acaoPesquisar.setEnabled(true);
    }

    private void atualizarInterfaceErro() {
        lblCidade.setText("Erro na Consulta");
        lblTemperatura.setText("X");
        lblCondicao.setText("Verifique o local ou sua conexão.");

        alterarTextoCard(cardExtremos, "Máx/Mín", "---");
        alterarTextoCard(cardUmidade, "Umidade", "---");
        alterarTextoCard(cardChuva, "Chuva", "---");
        alterarTextoCard(cardVento, "Vento", "---");

        acaoPesquisar.setEnabled(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            PainelClimaApp app = new PainelClimaApp();
            app.exibir();
        });
    }
}