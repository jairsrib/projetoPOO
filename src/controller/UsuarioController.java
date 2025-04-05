package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.Usuario;
//import utils.Utils;

/**
 *
 * @author iband
 */
public class UsuarioController {

    public boolean autenticar(String email, String senha) {
        //Montar o comando a ser executado
        //os ? são variáveis que são preenchidas mais adiante
        String sql = "SELECT * from USUARIO "
                + " WHERE email = ? and senha = ? "
                + " and ativo = true";

        //Cria uma instância do gerenciador de conexão(conexão com o banco de dados),
        GerenciadorConexao gerenciador = new GerenciadorConexao();
        //Declara as variáveis como nulas antes do try para poder usar no finally
        PreparedStatement comando = null;
        ResultSet resultado = null;

        try {
            //prepara o sql, analisando o formato e as váriaveis
            comando = gerenciador.prepararComando(sql);

            //define o valor de cada variável(?) pela posição em que aparece no sql
            comando.setString(1, email);
            comando.setString(2, senha);

            //executa o comando e guarda o resultado da consulta, o resultado é semelhante a uma grade
            resultado = comando.executeQuery();

            //resultado.next() - tenta avançar para a próxima linha, caso consiga retorna true
            if (resultado.next()) {
                //Se conseguiu avançar para a próxima linha é porque achou um usuário com aquele nome e senha
                return true;
            }
        } catch (SQLException e) {//caso ocorra um erro relacionado ao banco de dados
            JOptionPane.showMessageDialog(null, e.getMessage());//exibe popup com o erro
        } finally {//depois de executar o try, dando erro ou não executa o finally
            gerenciador.fecharConexao(comando, resultado);
        }
        return false;
    }

    public boolean inserirUsuario(Usuario usu) {

        String sql = "insert into usuario(nome, email, senha, datanasc, ativo) "
                + " values (?, ?, ?, ?, ?)";

        GerenciadorConexao gerenciador = new GerenciadorConexao();
        //Declara as variáveis como nulas antes do try para poder usar no finally
        PreparedStatement comando = null;
        try {
            comando = gerenciador.prepararComando(sql);
            comando.setString(1, usu.getNome());
            comando.setString(2, usu.getEmail());
            comando.setString(3, usu.getSenha());
            comando.setDate(4, new java.sql.Date(usu.getDataNasc().getTime()));
            comando.setBoolean(5, usu.isAtivo());
            comando.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            gerenciador.fecharConexao(comando);
        }
        return false;
    }

    public boolean alterarDados(Usuario usu) {
        String sql = "update usuario "
                + "set nome = ?, email = ?, senha = ?, datanasc = ?, ativo = ? "
                + "where pkusuario = ?";
        GerenciadorConexao gerenciador = new GerenciadorConexao();
        //Declara as variáveis como nulas antes do try para poder usar no finally
        PreparedStatement comando = null;
        try {
            comando = gerenciador.prepararComando(sql);
            comando.setString(1, usu.getNome());
            comando.setString(2, usu.getEmail());
            comando.setString(3, usu.getSenha());
            comando.setDate(4, new java.sql.Date(usu.getDataNasc().getTime()));
            comando.setBoolean(5, usu.isAtivo());
            comando.setInt(6, usu.getPkUsuario());

            comando.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            gerenciador.fecharConexao(comando);
        }
        return false;
    }

    public Usuario buscarporPK(int pkusuario) {
        String sql = "select * from usuario where pkusuario = ?";
        GerenciadorConexao gerenciador = new GerenciadorConexao();
        PreparedStatement comando = null;
        ResultSet resultado = null;

        Usuario usu = new Usuario();
        try {
            comando = gerenciador.prepararComando(sql);
            comando.setInt(1, pkusuario);
            resultado = comando.executeQuery();
            if (resultado.next()) {
                usu.setPkUsuario(resultado.getInt("pkusuario"));
                usu.setNome(resultado.getString("nome"));
                usu.setEmail(resultado.getString("email"));
                usu.setSenha(resultado.getString("senha"));
                usu.setDataNasc(resultado.getDate("datanasc"));
                usu.setAtivo(resultado.getBoolean("ativo"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(
                    Level.SEVERE, null, ex);
        }finally{
            gerenciador.fecharConexao(comando, resultado);
        }
        return usu;
    }
}
