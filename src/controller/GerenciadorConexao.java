package controller;

/**
 *
 * @author iband
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class GerenciadorConexao {
  //static - terá o mesmo valor em todas as instâncias da classe
  //final - o valor da variável não mudará
  private static final String URL = "jdbc:mysql://127.0.0.1:3306/dbmodelo";
  private static final String USER = "root";
  private static final String PASSWORD = "";

  private Connection conexao;
  
  public GerenciadorConexao() {
    try {//tentar executar esse bloco de código
      conexao = DriverManager.getConnection(URL, USER, PASSWORD);
    } catch (SQLException e) { //caso ocora um SQLException irá executar o seguinte tratamento
      JOptionPane.showMessageDialog(null, e.getMessage().toString());
    }
  }
  
  //responsável por analisar o sql a ser executado (select, insert, delete, ...)
  public PreparedStatement prepararComando(String sql) {
    PreparedStatement comando = null;

    try {
      comando = conexao.prepareStatement(sql);
    } catch (SQLException erro) {
      JOptionPane.showMessageDialog(null, "Erro ao preparar comando: " + erro);
    }

    return comando;
  }
  
  //1ª definição
  public void fecharConexao() {
    try {
      if (conexao != null) {
        conexao.close();
      }
    } catch (SQLException erro) {
      Logger.getLogger(GerenciadorConexao.class.getName())
              .log(Level.SEVERE, null, erro);
    }
  }
  
  //2ª definição
  public void fecharConexao(PreparedStatement comando) {
    fecharConexao();

    try {
      if (comando != null) {
        comando.close();
      }
    } catch (SQLException erro) {
      Logger.getLogger(GerenciadorConexao.class.getName())
              .log(Level.SEVERE, null, erro);
    }
  }
  
  //3ª
  public void fecharConexao(PreparedStatement comando, ResultSet resultado) {
    fecharConexao(comando);

    try {
      if (resultado != null) {
        resultado.close();
      }
    } catch (SQLException erro) {
      Logger.getLogger(GerenciadorConexao.class.getName())
              .log(Level.SEVERE, null, erro);
    }
  }
}
