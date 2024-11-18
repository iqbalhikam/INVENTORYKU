package com.inventory.main;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.inventory.component.Message;
import com.inventory.component.PanelCover;
import com.inventory.component.PanelLoading;
import com.inventory.component.PanelLoginAndRegister;
import com.inventory.component.PanelVerifyCode;
import com.inventory.connection.DatabaseConnection;
import com.inventory.model.ModelLogin;
import com.inventory.model.ModelMessage;
import com.inventory.model.ModelUser;
import com.inventory.service.ServiceMail;
import com.inventory.service.ServiceUser;
import com.inventory.system.service.ServiceData;
import com.inventory.system.session.UserSession;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.prefs.Preferences;
import javax.swing.JLayeredPane;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import raven.toast.Notifications;

public class Main extends javax.swing.JFrame {
  
//      private final Connection con = DatabaseConnection.getInstance().getConnection();
  private final DecimalFormat df = new DecimalFormat("##0.###", DecimalFormatSymbols.getInstance(Locale.US));
  private MigLayout layout;
  private PanelCover cover;
  private PanelLoading loading;
  private PanelVerifyCode verifyCode;
  private PanelLoginAndRegister loginAndRegister;
  private boolean isLogin;
  private final double addSize = 30;
  private final double coverSize = 40;
  private final double loginSize = 60;
  private ServiceUser service;
  private static final String PREF_USER_ID_KEY = "userId";
  private ServiceData serviceData;
  
  public Main() {
    initComponents();
    init();
    Notifications.getInstance().setJFrame(this);
  }
  
  
//      fungsi untuk load id user dari local storage/ penyimpanan local. di jalankan ketika aplikasi di buka
  public static int loadUserIdFromPersistentStorage() {
    Preferences prefs = Preferences.userNodeForPackage(Main.class);
    return prefs.getInt(PREF_USER_ID_KEY, 0);
  }
  
//      fungsi untuk menympan id user dari hasil login user ke dalam local storage/ penyimpanan local. di jalankan ketika login
  private static void saveUserIdToPersistentStorage(ModelUser userId) {
    Preferences prefs = Preferences.userNodeForPackage(Main.class);
    prefs.putInt(PREF_USER_ID_KEY, userId.getUserID());
  }
  
//      fungsi untuk menghapus id user dari local storage/ penyimpanan local. di jalankan ketika click logout
  public static void clearUserIdFromPersistentStorage() {
    Preferences prefs = Preferences.userNodeForPackage(Main.class);
    prefs.remove(PREF_USER_ID_KEY);
  }
  
  private void init() {
    // Membuat instance dari ServiceUser
    service = new ServiceUser();
    // Membuat instance dari ServiceData
    serviceData = new ServiceData();
    // Mengatur layout menggunakan MigLayout dengan properti "fill, insets 0"
    layout = new MigLayout("fill, insets 0");
    // Membuat panel cover
    cover = new PanelCover();
    // Membuat panel loading
    loading = new PanelLoading();
    // Membuat panel verifikasi kode
    verifyCode = new PanelVerifyCode();
    
    // Menambahkan event listener untuk tombol register
    ActionListener eventRegister = (ActionEvent ae) -> {
      register();
    };
    
    // Menambahkan event listener untuk tombol login
    ActionListener eventLogin = (ActionEvent ae) -> {
      login();
    };
    
    // Membuat panel login dan register dengan event register dan login
    loginAndRegister = new PanelLoginAndRegister(eventRegister, eventLogin);
    
    // Membuat target animasi untuk mengatur tampilan selama animasi
    TimingTarget target = new TimingTargetAdapter() {
      @Override
      public void timingEvent(float fraction) {
        double fractionCover;
        double fractionLogin;
        double size = coverSize;
        
        // Mengatur ukuran cover berdasarkan fraction animasi
        if (fraction <= 0.5f) {
          size += fraction * addSize;
        } else {
          size += addSize - fraction * addSize;
        }
        
        // Mengatur tampilan untuk kondisi login
        if (isLogin) {
          fractionCover = 1f - fraction;
          fractionLogin = fraction;
          if (fraction >= 0.5f) {
            cover.registerRight(fractionCover * 100);
          } else {
            cover.loginRight(fractionLogin * 100);
          }
        } else {
          // Mengatur tampilan untuk kondisi register
          fractionCover = fraction;
          fractionLogin = 1f - fraction;
          if (fraction <= 0.5f) {
            cover.registerLeft(fraction * 100);
          } else {
            cover.loginLeft((1f - fraction) * 100);
          }
        }
        
        // Menampilkan panel register jika fraction >= 0.5
        if (fraction >= 0.5f) {
          loginAndRegister.showRegister(isLogin);
        }
        
        // Mengatur posisi dan ukuran komponen menggunakan MigLayout
        fractionCover = Double.parseDouble(df.format(fractionCover));
        fractionLogin = Double.parseDouble(df.format(fractionLogin));
        layout.setComponentConstraints(cover, "width " + size + "%, pos " + fractionCover + "al 0 n 100%");
        layout.setComponentConstraints(loginAndRegister, "width " + loginSize + "%, pos " + fractionLogin + "al 0 n 100%");
        bg.revalidate();
      }
      
      @Override
      public void end() {
        // Membalik nilai isLogin setelah animasi selesai
        isLogin = !isLogin;
      }
    };
    
    // Membuat animator dengan durasi 800ms dan target animasi yang telah dibuat
    Animator animator = new Animator(800, target);
    animator.setAcceleration(0.5f);
    animator.setDeceleration(0.5f);
    animator.setResolution(0);  // untuk animasi yang halus
    
    // Mengatur layout dan layer untuk komponen-komponen pada bg
    bg.setLayout(layout);
    bg.setLayer(loading, JLayeredPane.POPUP_LAYER);
    bg.setLayer(verifyCode, JLayeredPane.POPUP_LAYER);
    bg.add(loading, "pos 0 0 100% 100%");
    bg.add(verifyCode, "pos 0 0 100% 100%");
    bg.add(cover, "width " + coverSize + "%, pos 0al 0 n 100%");
    bg.add(loginAndRegister, "width " + loginSize + "%, pos 1al 0 n 100%"); // 1al sebagai 100%
    
    // Menambahkan event listener untuk cover
    cover.addEvent((ActionEvent ae) -> {
      // Memulai animasi jika tidak sedang berjalan
      if (!animator.isRunning()) {
        animator.start();
      }
    });
    
    // Menambahkan event listener untuk tombol OK pada panel verifikasi kode
    verifyCode.addEventButtonOK((ActionEvent ae) -> {
      try {
        // Mendapatkan data user dari panel login dan register
        ModelUser user = loginAndRegister.getUser();
        // Memverifikasi kode dengan user ID
        if (service.verifyCodeWithUser(user.getUserID(), verifyCode.getInputCode())) {
          service.doneVerify(user.getUserID());
          showMessage(Message.MessageType.SUCCESS, "Register sukses");
          verifyCode.setVisible(false);
        } else {
          showMessage(Message.MessageType.ERROR, "Verify code incorrect");
        }
      } catch (SQLException e) {
        showMessage(Message.MessageType.ERROR, "Error");
      }
    });
  }
  
  
//    REGISTER START
  private void register() {
    ModelUser user = loginAndRegister.getUser();
    
    try {
      if (user.getUserName().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
        showMessage(Message.MessageType.ERROR, "lengkapi form di bawah!!");
        
      }else if (service.checkDuplicateUser(user.getUserName())) {
        showMessage(Message.MessageType.ERROR, "User name already exit");
      } else if (service.checkDuplicateEmail(user.getEmail())) {
        showMessage(Message.MessageType.ERROR, "Email already exit");
      } else {
        service.insertUser(user);
        sendMain(user);
      }
    } catch (SQLException e) {
      showMessage(Message.MessageType.ERROR, "Error Register");
      System.out.println("erorr : \n" + e);
    }
  }
//    REGISTER END
  
//    LOGIN START
  private void login() {
    ModelLogin data = loginAndRegister.getDataLogin();
    System.out.println("Email : " + data.getEmail() + "\nPassword : " + data.getPassword());
    try {
      ModelUser user = service.login(data);
      if (user != null) {
        this.dispose();
        saveUserIdToPersistentStorage(user);
        MainSystem.main(user);
        
      } else {
        showMessage(Message.MessageType.ERROR, "Email and Password incorrect");
      }
      
    } catch (SQLException e) {
      showMessage(Message.MessageType.ERROR, "Error Login");
      System.out.println(e);
    }
  }
//    LOGIN END
  
  
  
  private void sendMain(ModelUser user) {
    // Membuat thread baru untuk menjalankan pengiriman email
    new Thread(() -> {
      // Menampilkan panel loading
      loading.setVisible(true);
      // Mengirim email verifikasi menggunakan ServiceMail
      ModelMessage ms = new ServiceMail().sendMain(user.getEmail(), user.getVerifyCode());
      // Memeriksa apakah pengiriman email berhasil
      if (ms.isSuccess()) {
        // Menyembunyikan panel loading
        loading.setVisible(false);
        // Menampilkan panel verifikasi kode
        verifyCode.setVisible(true);
      } else {
        // Menyembunyikan panel loading jika gagal
        loading.setVisible(false);
        // Mencetak pesan error ke konsol
        System.out.println("error: \n" + ms.getMessage());
      }
    }).start(); // Memulai thread
  }
  
  
  public void showMessage(Message.MessageType messageType, String message) {
    // Membuat instance pesan baru
    Message ms = new Message();
    // Menampilkan pesan dengan tipe dan isi pesan
    ms.showMessage(messageType, message);
    
    // Membuat target animasi untuk menampilkan dan menyembunyikan pesan
    TimingTarget target = new TimingTargetAdapter() {
      @Override
      public void begin() {
        // Jika pesan belum ditampilkan
        if (!ms.isShow()) {
          // Menambahkan pesan ke bg pada posisi awal dan index pertama
          bg.add(ms, "pos 0.5al -30", 0); // Menyisipkan ke bg dengan index pertama 0
          // Menampilkan pesan
          ms.setVisible(true);
          // Melakukan repaint pada bg
          bg.repaint();
        }
      }
      
      @Override
      public void timingEvent(float fraction) {
        float f;
        // Mengatur posisi pesan selama animasi
        if (ms.isShow()) {
          f = 40 * (1f - fraction);
        } else {
          f = 40 * fraction;
        }
        layout.setComponentConstraints(ms, "pos 0.5al " + (int) (f - 30));
        bg.repaint();
        bg.revalidate();
      }
      
      @Override
      public void end() {
        // Jika pesan sedang ditampilkan
        if (ms.isShow()) {
          // Menghapus pesan dari bg
          bg.remove(ms);
          bg.repaint();
          bg.revalidate();
        } else {
          // Menandai pesan sebagai ditampilkan
          ms.setShow(true);
        }
      }
    };
    
    // Membuat animator dengan durasi 300ms dan target animasi yang telah dibuat
    Animator animator = new Animator(300, target);
    animator.setResolution(0);
    animator.setAcceleration(0.5f);
    animator.setDeceleration(0.5f);
    animator.start();
    
    // Membuat thread baru untuk menjalankan animasi kedua setelah 2 detik
    new Thread(() -> {
      try {
        // Menunggu selama 2 detik
        Thread.sleep(2000);
        // Memulai animasi kedua untuk menyembunyikan pesan
        animator.start();
      } catch (InterruptedException e) {
        // Menangani kesalahan jika thread terinterupsi
        System.err.println(e);
      }
    }).start();
  }
  
  @SuppressWarnings("unchecked")
      // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
      private void initComponents() {

            bg = new javax.swing.JLayeredPane();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setUndecorated(true);

            bg.setBackground(new java.awt.Color(255, 255, 255));
            bg.setOpaque(true);

            javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
            bg.setLayout(bgLayout);
            bgLayout.setHorizontalGroup(
                  bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGap(0, 933, Short.MAX_VALUE)
            );
            bgLayout.setVerticalGroup(
                  bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGap(0, 537, Short.MAX_VALUE)
            );

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(bg, javax.swing.GroupLayout.Alignment.TRAILING)
            );
            layout.setVerticalGroup(
                  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(bg)
            );

            pack();
            setLocationRelativeTo(null);
      }// </editor-fold>//GEN-END:initComponents
      
      public static void main(String args[]) {
        FlatRobotoFont.install();
        FlatLightLaf.setup();
        
        
        
        
        try {
          DatabaseConnection.getInstance().connectToDatabase();
        } catch (SQLException e) {
          System.out.println(e);
        }
        java.awt.EventQueue.invokeLater(() -> {
          int userId = loadUserIdFromPersistentStorage();
          if (userId != 0) {
            UserSession.getInstance().setUserId(userId);
            
            if (UserSession.getInstance().isLoggedIn()) {
              int userIdSession = UserSession.getInstance().getUserId();
              ModelUser user = new ModelUser();
              user.setUserID(userIdSession);
              MainSystem.main(user);
            }
            
          } else {
            new Main().setVisible(true);
          }
        });
      }

      // Variables declaration - do not modify//GEN-BEGIN:variables
      private javax.swing.JLayeredPane bg;
      // End of variables declaration//GEN-END:variables
}
