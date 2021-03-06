/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainProgram;

import Database.Koneksi;
import com.sun.glass.events.KeyEvent;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Fajar Adi Setyawan
 */
public class SignUp extends javax.swing.JFrame {
    public Connection con;
    public ResultSet rs;
    public Statement st;
    /**
     * Creates new form SignUp
     */
    public SignUp() {
        initComponents();
        Koneksi classKoneksi = new Koneksi();
        con = classKoneksi.getKoneksi();
    }
    
    private void buatakun(){
        String nama = txt_namalengkap.getText();
        String user = txt_user.getText();
        String email = txt_email.getText();
        String pass = input_pass.getText();
        String repass = input_repass.getText();
        String SQLbuat = "INSERT INTO `tb_user` (`nama_lengkap`, `username`, `email`, `password`) VALUES (?, ?, ?, ?)";
        try{
            if("".equals(nama) || "".equals(user) || "".equals(email) || "".equals(input_pass)){
                JOptionPane.showMessageDialog(null, "Data Belum Di Masukkan", "Pemberitahuan", JOptionPane.ERROR_MESSAGE);
            }else if(!repass.equals(pass)){
                JOptionPane.showMessageDialog(null, "Password Tidak Sama !", "Pemberitahuan", JOptionPane.ERROR_MESSAGE);
            }else{
                PreparedStatement pst = con.prepareStatement(SQLbuat);
                pst.setString(1, nama);
                pst.setString(2, user);
                pst.setString(3, email);
                pst.setString(4, pass);
                    
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "SUCESS");
                new Login().setVisible(true);
                this.dispose();
            }
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "GAGAL", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txt_namalengkap = new MainProgram.CustomTextNama();
        txt_email = new MainProgram.CustomTextEmail();
        txt_user = new MainProgram.CustomTextField();
        input_pass = new MainProgram.CustomPasswordField();
        input_repass = new MainProgram.CustomRePasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btn_signup = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        show_pass = new javax.swing.JLabel();
        hide_pass = new javax.swing.JLabel();
        show_repass = new javax.swing.JLabel();
        hide_repass = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 102));
        setMinimumSize(new java.awt.Dimension(400, 500));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/CloseB25px.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/Close25pxS.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 0, -1, -1));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/MinimizeB.png"))); // NOI18N
        jButton2.setBorder(null);
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/Minimisize25pxS.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(338, 0, -1, 25));

        txt_namalengkap.setBackground(new java.awt.Color(214, 217, 223));
        txt_namalengkap.setFont(new java.awt.Font("Georgia", 0, 16)); // NOI18N
        txt_namalengkap.setBorder(null);
        txt_namalengkap.setMinimumSize(new java.awt.Dimension(250, 25));
        txt_namalengkap.setOpaque(false);
        txt_namalengkap.setPreferredSize(new java.awt.Dimension(250, 30));
        txt_namalengkap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namalengkapActionPerformed(evt);
            }
        });
        getContentPane().add(txt_namalengkap, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 102, -1, -1));

        txt_email.setBackground(new java.awt.Color(214, 217, 223));
        txt_email.setFont(new java.awt.Font("Georgia", 0, 16)); // NOI18N
        txt_email.setBorder(null);
        txt_email.setMinimumSize(new java.awt.Dimension(250, 25));
        txt_email.setOpaque(false);
        txt_email.setPreferredSize(new java.awt.Dimension(250, 30));
        txt_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_emailActionPerformed(evt);
            }
        });
        getContentPane().add(txt_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 246, -1, -1));

        txt_user.setBackground(new java.awt.Color(214, 217, 223));
        txt_user.setFont(new java.awt.Font("Georgia", 0, 16)); // NOI18N
        txt_user.setBorder(null);
        txt_user.setMinimumSize(new java.awt.Dimension(250, 30));
        txt_user.setOpaque(false);
        txt_user.setPreferredSize(new java.awt.Dimension(250, 30));
        txt_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_userActionPerformed(evt);
            }
        });
        getContentPane().add(txt_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 174, -1, -1));

        input_pass.setBackground(new java.awt.Color(214, 217, 223));
        input_pass.setFont(new java.awt.Font("Georgia", 0, 16)); // NOI18N
        input_pass.setBorder(null);
        input_pass.setNextFocusableComponent(input_repass);
        input_pass.setOpaque(false);
        input_pass.setPreferredSize(new java.awt.Dimension(250, 30));
        getContentPane().add(input_pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 318, 220, -1));

        input_repass.setBackground(new java.awt.Color(214, 217, 223));
        input_repass.setFont(new java.awt.Font("Georgia", 0, 16)); // NOI18N
        input_repass.setBorder(null);
        input_repass.setOpaque(false);
        input_repass.setPreferredSize(new java.awt.Dimension(250, 30));
        input_repass.setRequestFocusEnabled(false);
        input_repass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                input_repassKeyPressed(evt);
            }
        });
        getContentPane().add(input_repass, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 390, 220, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/icons8_name_25px.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 102, -1, 30));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/icons8_user_25px.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 174, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/icons8_email_25px_1.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 251, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/icons8_password_25px.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 395, -1, -1));

        btn_signup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_SignUP.png"))); // NOI18N
        btn_signup.setBorder(null);
        btn_signup.setContentAreaFilled(false);
        btn_signup.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_signup.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/Btn_signUP2.png"))); // NOI18N
        btn_signup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_signupActionPerformed(evt);
            }
        });
        getContentPane().add(btn_signup, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 456, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/icons8_password_25px.png"))); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 318, -1, -1));

        jLabel5.setFont(new java.awt.Font("Gadugi", 1, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Buat Akun Baru");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 400, -1));

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setOpaque(true);
        jLabel7.setPreferredSize(new java.awt.Dimension(250, 2));
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 132, -1, -1));

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setOpaque(true);
        jLabel8.setPreferredSize(new java.awt.Dimension(250, 2));
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 204, -1, -1));

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setOpaque(true);
        jLabel9.setPreferredSize(new java.awt.Dimension(250, 2));
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 276, -1, -1));

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setOpaque(true);
        jLabel10.setPreferredSize(new java.awt.Dimension(250, 2));
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 348, -1, -1));

        jLabel11.setBackground(new java.awt.Color(0, 0, 0));
        jLabel11.setOpaque(true);
        jLabel11.setPreferredSize(new java.awt.Dimension(250, 2));
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 420, -1, -1));

        show_pass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/icons8_visible_20px.png"))); // NOI18N
        show_pass.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        show_pass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                show_passMouseClicked(evt);
            }
        });
        getContentPane().add(show_pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 320, -1, 20));

        hide_pass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/icons8_invisible_20px.png"))); // NOI18N
        hide_pass.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hide_pass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hide_passMouseClicked(evt);
            }
        });
        getContentPane().add(hide_pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 320, -1, 20));

        show_repass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/icons8_visible_20px.png"))); // NOI18N
        show_repass.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        show_repass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                show_repassMouseClicked(evt);
            }
        });
        getContentPane().add(show_repass, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 390, -1, 30));

        hide_repass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/icons8_invisible_20px.png"))); // NOI18N
        hide_repass.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hide_repass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hide_repassMouseClicked(evt);
            }
        });
        getContentPane().add(hide_repass, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 390, 20, 30));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/Back25px.png"))); // NOI18N
        jButton4.setBorder(null);
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/Back25pxS.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int opt = JOptionPane.showConfirmDialog(null, "KELUAR ?", "KELUAR", JOptionPane.YES_NO_OPTION);
        if(opt==0){
            System.exit(0);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_namalengkapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namalengkapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namalengkapActionPerformed

    private void txt_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailActionPerformed

    private void txt_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_userActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_userActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btn_signupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_signupActionPerformed
        buatakun();
    }//GEN-LAST:event_btn_signupActionPerformed

    private void hide_passMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hide_passMouseClicked
        show_pass.setVisible(true);
        show_pass.setEnabled(true);
        input_pass.setEchoChar((char)8226);
        hide_pass.setVisible(false);
        hide_pass.setEnabled(false);
    }//GEN-LAST:event_hide_passMouseClicked

    private void show_passMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_show_passMouseClicked
        hide_pass.setVisible(true);
        hide_pass.setEnabled(true);
        input_pass.setEchoChar((char)0);
        show_pass.setVisible(false);
        show_pass.setEnabled(false);
    }//GEN-LAST:event_show_passMouseClicked

    private void show_repassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_show_repassMouseClicked
        hide_repass.setVisible(true);
        hide_repass.setEnabled(true);
        input_repass.setEchoChar((char)0);
        show_repass.setVisible(false);
        show_repass.setEnabled(false);
    }//GEN-LAST:event_show_repassMouseClicked

    private void hide_repassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hide_repassMouseClicked
        show_repass.setVisible(true);
        show_repass.setEnabled(true);
        input_repass.setEchoChar((char)8226);
        hide_repass.setVisible(false);
        hide_repass.setEnabled(false);
    }//GEN-LAST:event_hide_repassMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        new Login().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void input_repassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_input_repassKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            buatakun();
        }
    }//GEN-LAST:event_input_repassKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SignUp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_signup;
    private javax.swing.JLabel hide_pass;
    private javax.swing.JLabel hide_repass;
    private javax.swing.JPasswordField input_pass;
    private javax.swing.JPasswordField input_repass;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel show_pass;
    private javax.swing.JLabel show_repass;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_namalengkap;
    private javax.swing.JTextField txt_user;
    // End of variables declaration//GEN-END:variables
}
