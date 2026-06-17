package com.clima;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

public class AppClima {

    private JFrame frame;

    private static final Color FUNDO     = new Color(0x0F, 0x17, 0x2A);
    private static final Color PAINEL_BG = new Color(0x1A, 0x25, 0x3D);
    private static final Color CARD_BG   = new Color(0x22, 0x30, 0x4F);
    private static final Color BORDA     = new Color(0x2E, 0x42, 0x6B);
    private static final Color AZUL      = new Color(0x38, 0x8B, 0xFF);
    private static final Color TEXTO     = new Color(0xE8, 0xF0, 0xFF);
    private static final Color SUBTEXTO  = new Color(0x7A, 0x96, 0xC8);
    private static final Color ERRO      = new Color(0xFF, 0x5A, 0x5A);
    private static final Color VERDE     = new Color(0x4C, 0xD9, 0x8A);
    private static final Color AMARELO   = new Color(0xFF, 0xC8, 0x3C);

    private static final String FONTE = "Dialog";

    private JTextField campoCidade;
    private JButton    botaoBuscar;
    private JLabel     labelStatus;
    private JPanel     painelResultado;

    private JLabel labelEndereco;
    private JLabel labelTemp;
    private JLabel labelCondicao;
    private JLabel labelTempMax;
    private JLabel labelTempMin;
    private JLabel labelUmidade;
    private JLabel labelPrecip;
    private JLabel labelVento;
    private JLabel labelDirecao;

    public AppClima() {
        criarJanela();
        criarInterface();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void criarJanela() {
        frame = new JFrame("Clima Agora");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setBackground(FUNDO);
    }

    private void criarInterface() {
        JPanel raiz = new JPanel(new BorderLayout(0, 12));
        raiz.setBackground(FUNDO);
        raiz.setBorder(new EmptyBorder(20, 20, 20, 20));

        raiz.add(criarCabecalho(),      BorderLayout.NORTH);
        raiz.add(criarCentro(),         BorderLayout.CENTER);

        frame.add(raiz);
    }

    private JPanel criarCabecalho() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(FUNDO);
        p.setBorder(new EmptyBorder(0, 0, 4, 0));

        JLabel titulo = new JLabel("Clima Agora");
        titulo.setFont(new Font(FONTE, Font.BOLD, 26));
        titulo.setForeground(TEXTO);

        JLabel sub = new JLabel("Visual Crossing Weather API");
        sub.setFont(new Font(FONTE, Font.PLAIN, 12));
        sub.setForeground(SUBTEXTO);

        JPanel col = new JPanel(new GridLayout(2, 1));
        col.setBackground(FUNDO);
        col.add(titulo);
        col.add(sub);

        p.add(col, BorderLayout.WEST);
        return p;
    }

    private JPanel criarCentro() {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(FUNDO);

        p.add(criarPainelBusca(),    BorderLayout.NORTH);
        p.add(criarAreaResultado(),  BorderLayout.CENTER);

        return p;
    }

    private JPanel criarPainelBusca() {
        JPanel p = new JPanel(new BorderLayout(0, 8));
        p.setBackground(PAINEL_BG);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDA, 1),
            new EmptyBorder(14, 14, 14, 14)
        ));

        JLabel rotulo = new JLabel("Cidade");
        rotulo.setFont(new Font(FONTE, Font.PLAIN, 12));
        rotulo.setForeground(SUBTEXTO);

        campoCidade = new JTextField(24);
        campoCidade.setFont(new Font(FONTE, Font.PLAIN, 14));
        campoCidade.setForeground(TEXTO);
        campoCidade.setBackground(FUNDO);
        campoCidade.setCaretColor(TEXTO);
        campoCidade.setMargin(new Insets(6, 8, 6, 8));
        campoCidade.setBorder(BorderFactory.createLineBorder(BORDA, 1));
        campoCidade.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) iniciarBusca();
            }
        });

        botaoBuscar = new JButton("Buscar");
        botaoBuscar.setFont(new Font(FONTE, Font.BOLD, 14));
        botaoBuscar.setForeground(Color.WHITE);
        botaoBuscar.setBackground(AZUL);
        botaoBuscar.setFocusPainted(false);
        botaoBuscar.setBorderPainted(false);
        botaoBuscar.setOpaque(true);
        botaoBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botaoBuscar.setMargin(new Insets(8, 0, 8, 0));
        botaoBuscar.addActionListener(e -> iniciarBusca());

        p.add(rotulo,      BorderLayout.NORTH);
        p.add(campoCidade, BorderLayout.CENTER);
        p.add(botaoBuscar, BorderLayout.SOUTH);

        return p;
    }

    private JPanel criarAreaResultado() {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(FUNDO);

        labelStatus = new JLabel(" ");
        labelStatus.setFont(new Font(FONTE, Font.PLAIN, 13));
        labelStatus.setForeground(SUBTEXTO);

        painelResultado = criarPainelResultado();
        painelResultado.setVisible(false);

        p.add(labelStatus,    BorderLayout.NORTH);
        p.add(painelResultado, BorderLayout.CENTER);

        return p;
    }

    private JPanel criarPainelResultado() {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(FUNDO);

        labelEndereco = new JLabel(" ");
        labelEndereco.setFont(new Font(FONTE, Font.PLAIN, 13));
        labelEndereco.setForeground(SUBTEXTO);

        labelTemp = new JLabel(" ");
        labelTemp.setFont(new Font(FONTE, Font.BOLD, 52));
        labelTemp.setForeground(AMARELO);

        labelCondicao = new JLabel(" ");
        labelCondicao.setFont(new Font(FONTE, Font.PLAIN, 15));
        labelCondicao.setForeground(TEXTO);

        JPanel topo = new JPanel(new GridLayout(3, 1, 0, 4));
        topo.setBackground(PAINEL_BG);
        topo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDA, 1),
            new EmptyBorder(16, 16, 12, 16)
        ));
        topo.add(labelEndereco);
        topo.add(labelTemp);
        topo.add(labelCondicao);

        labelTempMax = new JLabel();
        labelTempMin = new JLabel();
        labelUmidade = new JLabel();
        labelPrecip  = new JLabel();
        labelVento   = new JLabel();
        labelDirecao = new JLabel();

        JPanel grade = new JPanel(new GridLayout(3, 2, 10, 10));
        grade.setBackground(FUNDO);
        grade.add(card("Maxima",       labelTempMax, VERDE));
        grade.add(card("Minima",       labelTempMin, AZUL));
        grade.add(card("Umidade",      labelUmidade, AZUL));
        grade.add(card("Precipitacao", labelPrecip,  AZUL));
        grade.add(card("Vento",        labelVento,   AZUL));
        grade.add(card("Direcao",      labelDirecao, AZUL));

        p.add(topo,  BorderLayout.NORTH);
        p.add(grade, BorderLayout.CENTER);

        return p;
    }

    private JPanel card(String titulo, JLabel labelValor, Color corValor) {
        JPanel p = new JPanel(new GridLayout(2, 1, 0, 4));
        p.setBackground(CARD_BG);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDA, 1),
            new EmptyBorder(10, 12, 10, 12)
        ));

        JLabel lbTitulo = new JLabel(titulo);
        lbTitulo.setFont(new Font(FONTE, Font.PLAIN, 11));
        lbTitulo.setForeground(SUBTEXTO);

        labelValor.setFont(new Font(FONTE, Font.BOLD, 18));
        labelValor.setForeground(corValor);

        p.add(lbTitulo);
        p.add(labelValor);
        return p;
    }

    private void iniciarBusca() {
        String cidade = campoCidade.getText().trim();

        if (cidade.isEmpty()) {
            mostrarStatus("Informe o nome de uma cidade.", ERRO);
            return;
        }

        botaoBuscar.setEnabled(false);
        botaoBuscar.setText("Buscando...");
        painelResultado.setVisible(false);
        mostrarStatus("Consultando API...", SUBTEXTO);

        SwingWorker<DadosClima, Void> worker = new SwingWorker<DadosClima, Void>() {
            protected DadosClima doInBackground() throws Exception {
                String chave = ConfiguracaoApp.lerApiKey();
                return new ClimaServico(chave).buscar(cidade);
            }

            protected void done() {
                try {
                    exibirResultado(get());
                    mostrarStatus("Dados atualizados.", VERDE);
                } catch (java.util.concurrent.ExecutionException ex) {
                    mostrarStatus(ex.getCause().getMessage(), ERRO);
                } catch (Exception ex) {
                    mostrarStatus("Erro: " + ex.getMessage(), ERRO);
                } finally {
                    botaoBuscar.setEnabled(true);
                    botaoBuscar.setText("Buscar");
                }
            }
        };

        worker.execute();
    }

    private void exibirResultado(DadosClima d) {
        labelEndereco.setText(d.getEnderecoResolvido());
        labelTemp.setText(String.format("%.1f °C", d.getTempAtual()));
        labelCondicao.setText(d.getCondicao());
        labelTempMax.setText(String.format("%.1f °C", d.getTempMax()));
        labelTempMin.setText(String.format("%.1f °C", d.getTempMin()));
        labelUmidade.setText(String.format("%.0f %%", d.getUmidade()));
        labelPrecip.setText(String.format("%.1f mm", d.getPrecipitacao()));
        labelVento.setText(String.format("%.1f km/h", d.getVelocidadeVento()));
        labelDirecao.setText(String.format("%s (%.0f°)", d.getDirecaoVentoTexto(), d.getDirecaoVento()));
        painelResultado.setVisible(true);
        frame.pack();
    }

    private void mostrarStatus(String msg, Color cor) {
        labelStatus.setText(msg);
        labelStatus.setForeground(cor);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AppClima();
            }
        });
    }
}