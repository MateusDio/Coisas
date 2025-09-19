
package DAO;

import DTO.UsuarioDTO;
import VIEW.CadastroUsuario;
import VIEW.TelaPrincipal;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class UsuarioDAO {
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public void logar(UsuarioDTO objusuarioDTO){
        String sql = "select * from tb_usuarios where login = ? and senha = ?";
        conexao = ConexaoDAO.conector();
        
        try{
            pst = conexao.prepareStatement(sql);
            pst.setString(1, objusuarioDTO.getLogin_usuario());
            pst.setString(2, objusuarioDTO.getSenha_usuario());
            
            rs = pst.executeQuery();
            
            if(rs.next()){
                String perfil = rs.getString(5);
                System.out.println(perfil);
                
                if(perfil.equals("admin")){
                    TelaPrincipal pr = new TelaPrincipal();
                    pr.setVisible(true);
                    TelaPrincipal.MenuRel.setEnabled(true);
                    TelaPrincipal.subMenuUsuarios.setEnabled(true);
                    TelaPrincipal.lblUsuarioPrincipal.setText(rs.getString(2));
                    TelaPrincipal.lblUsuarioPrincipal.setForeground(Color.RED);
                    conexao.close();
                }else{
                    TelaPrincipal principal = new TelaPrincipal();
                    principal.setVisible(true);
                    principal.lblUsuarioPrincipal.setText(rs.getString(2));
                    TelaPrincipal.lblUsuarioPrincipal.setForeground(Color.BLUE);
                    conexao.close();
                
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "Usuário e/ou senha inválidos");
                
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "** tela login ***" + e);
        }
    }



    public void inserirUsuario(UsuarioDTO objUsuarioDTO){
        String sql = "insert into tb_usuario (id_usuario, usuario, login,senha, perfil)"
            + "values (?,?,?,?,?)";
        conexao = new ConexaoDAO().conector();
        
        try{
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objUsuarioDTO.getId_usuario());
            pst.setString(2, objUsuarioDTO.getNome_usuario());
            pst.setString(3, objUsuarioDTO.getLogin_usuario());
            pst.setString(4, objUsuarioDTO.getSenha_usuario());
            pst.setString(5, objUsuarioDTO.getPerfil_usuario());
            int add = pst.executeUpdate();
            if(add > 0){
                
                PesquisaAuto();
                pst.close();
                limparCampos();
                JOptionPane.showMessageDialog(null, "Usuário inserido com sucesso!");
                
            }
            
          
            
        } catch (Exception e){
            
            JOptionPane.showMessageDialog(null," Método Inserir " + e);
        }
        
    }
    
    
    
    public void pesquisar (UsuarioDTO objUsuarioDTO){
        String sql = "select * from tb_usuarios where id_usuario = ?";
        conexao = ConexaoDAO.conector();
        
        try{
            pst = conexao.prepareStatement(sql);
            pst.setInt (1, objUsuarioDTO.getId_usuario());
            rs = pst.executeQuery();
            if(rs.next()){
                CadastroUsuario.txtNomeUsu.setText(rs.getString(2));
                CadastroUsuario.txtLoginUsu.setText(rs.getString(3));
               CadastroUsuario.txtSenhaUsu.setText(rs.getString(4));
                CadastroUsuario.cboPerfilUsu.setSelectedItem(rs.getString(5));
                conexao.close();
            }else {
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado!");
                limparCampos();
                
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Método Pesquisar" + e);
        }
    }
    public void PesquisaAuto(){
        String sql = "select * from tb_usuarioss";
        conexao = ConexaoDAO.conector();
        
        try{
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) CadastroUsuario.TbUsuarios.getModel();
            model.setNumRows(0);
            while(rs.next()){
                int id = rs.getInt("id_usuario");
                String nome = rs.getString("usuario");
                String login = rs.getString("senha");
                String senha = rs.getString("senha");
                String perfil = rs.getString("perfil");
                model.addRow(new Object[]{id, nome, login, senha, perfil});
                
            }
            conexao.close();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Método Pesquisar Automático " + e);
        }
    }
    public void deletar (UsuarioDTO objUsuarioDTO){
        String sql = "delete from tb_usuarios where id_usuario = ?";
        conexao = ConexaoDAO.conector();
        
        try{
            pst = conexao.prepareStatement(sql);
            pst.setInt(1, objUsuarioDTO.getId_usuario());
            int del = pst.executeUpdate();
            if(del > 0){
                JOptionPane.showMessageDialog(null, "Usuário deletado com sucesso!");
                PesquisaAuto();
                conexao.close();
                limparCampos();
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, " Método deletar " + e);
            
        }
    }
    
    public void limparCampos(){
        CadastroUsuario.txtIdUsu.setText(null);
        CadastroUsuario.txtLoginUsu.setText(null);
        CadastroUsuario.txtNomeUsu.setText(null);
        CadastroUsuario.txtSenhaUsu.setText(null);
        CadastroUsuario.cboPerfilUsu.setSelected
    }
    
}
