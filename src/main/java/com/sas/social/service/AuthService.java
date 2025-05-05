//----------------------------------------------------------------------------------------------------------------
// Mevcut fonksiyonlar:
// - loginUser: Kullanıcı girişini doğrular ve kimlik tokeni döndürür
// - register: Yeni kullanıcı kaydı oluşturur
// - validateRegistration: Kayıt geçerliliğini kontrol eder
// - hashPassword: Şifreyi güvenli şekilde şifreler
// - validatePassword: Girilen şifrenin doğruluğunu kontrol eder
// - encryptUserId: Kullanıcı ID'sini şifreler
// - forgotPassword: Şifre sıfırlama işlemini gerçekleştirir
//
// İstisnalar (Exceptions):
// - AuthenticationException: Kimlik doğrulama hatalarında fırlatılır
// - RegistrationException: Kayıt işlemi sırasında oluşan hatalarda fırlatılır
//----------------------------------------------------------------------------------------------------------------

package com.sas.social.service;

import java.util.List;
import java.util.Optional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sas.social.dto.UserRegisterDto;
import com.sas.social.entity.User;
import com.sas.social.mapper.UserRegisterMapper;
import com.sas.social.repository.UserRepository;
//import com.sas.social.exception.AuthenticationException;
//import com.sas.social.exception.RegistrationException;

import jakarta.transaction.Transactional;

//----------------------------------------------------------------------------------------------------------------

//@Service
public class AuthService 
{
//    @Autowired
//    private UserRepository userRepository;
//    
//    @Autowired
//    private UserRegisterMapper userRegisterMapper;
//    
//    @Value("lc2s-29sk1-s982jk-s8cj1j2-sas-proje")
//    private String encryptionKey;
//    
//    @Value("${app.resetPasswordExpiryMinutes:30}")
//    private int resetPasswordExpiryMinutes;
//    
//    @Autowired
//    private EmailService emailService;
//
//    public String loginUser(String nameOrEmail, String password) throws AuthenticationException
//    {
//        Optional<User> userOpt;
//
//        if (nameOrEmail.contains("@"))
//        {
//            userOpt = userRepository.findByEmail(nameOrEmail);
//        }
//        else
//        {
//            userOpt = userRepository.findByName(nameOrEmail);
//        }
//
//        if (userOpt.isEmpty() || !validatePassword(password, userOpt.get().getPassword()))
//        {
//            throw new AuthenticationException("Geçersiz kullanıcı bilgileri");
//        }
//
//        try
//        {
//            return encryptUserId(userOpt.get().getId());
//        }
//        catch (Exception e)
//        {
//            throw new AuthenticationException("Kimlik doğrulama işlemi sırasında hata oluştu");
//        }
//    }
//
//    @Transactional
//    public String register(UserRegisterDto userRegisterDto) throws RegistrationException
//    {
//        validateRegistration(userRegisterDto);
//
//        User user = userRegisterMapper.toEntity(userRegisterDto);
//
//        user.setPassword(hashPassword(userRegisterDto.getPassword()));
//        
//        User savedUser = userRepository.save(user);
//
//        try
//        {
//            return encryptUserId(savedUser.getId());
//        }
//        catch (Exception e)
//        {
//            throw new RegistrationException("Kayıt işlemi sırasında hata oluştu");
//        }
//    }
//
//    private void validateRegistration(UserRegisterDto userRegisterDto) throws RegistrationException
//    {
//
//        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword()))
//        {
//            throw new RegistrationException("Şifreler eşleşmiyor");
//        }
//
//        if (userRepository.findByEmail(userRegisterDto.getEmail()).isPresent())
//        {
//            throw new RegistrationException("Bu e-posta adresi zaten kullanımda");
//        }
//        
//
//        if (userRepository.findByName(userRegisterDto.getName()).isPresent())
//        {
//            throw new RegistrationException("Bu kullanıcı adı zaten kullanımda");
//        }
//    }
//
//    private String hashPassword(String password)
//    {
//        try
//        {
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            byte[] hash = digest.digest(password.getBytes());
//            StringBuilder hexString = new StringBuilder();
//            
//            for (byte b : hash)
//            {
//                String hex = Integer.toHexString(0xff & b);
//                if (hex.length() == 1)
//                {
//                    hexString.append('0');
//                }
//                hexString.append(hex);
//            }
//            
//            return hexString.toString();
//        }
//        catch (NoSuchAlgorithmException e)
//        {
//            throw new RuntimeException("Şifre şifreleme işlemi sırasında hata oluştu", e);
//        }
//    }
//
//    private boolean validatePassword(String inputPassword, String storedHash)
//    {
//        String inputHash = hashPassword(inputPassword);
//        return inputHash.equals(storedHash);
//    }
//
//    private String encryptUserId(Long userId) throws Exception
//    {
//        SecretKeySpec secretKey = new SecretKeySpec(encryptionKey.getBytes(), "AES");
//        Cipher cipher = Cipher.getInstance("AES");
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//        
//        byte[] encryptedBytes = cipher.doFinal(userId.toString().getBytes());
//        return Base64.getEncoder().encodeToString(encryptedBytes);
//    }
//    
//    /**
//     * Şifre sıfırlama işlemini başlatır ve kullanıcıya sıfırlama bağlantısı gönderir
//     * @param email Kullanıcının e-posta adresi
//     * @throws AuthenticationException Geçersiz e-posta veya sıfırlama işlemi hatası durumunda
//     */
//    @Transactional
//    public void forgotPassword(String email) throws AuthenticationException 
//    {
//        Optional<User> userOpt = userRepository.findByEmail(email);
//        
//        if (userOpt.isEmpty()) 
//        {
//            throw new AuthenticationException("Bu e-posta adresiyle kayıtlı kullanıcı bulunamadı");
//        }
//        
//        User user = userOpt.get();
//        
//        // Benzersiz bir sıfırlama tokeni oluştur
//        String resetToken = UUID.randomUUID().toString();
//        
//        // Token'in son kullanma tarihini belirle
//        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(resetPasswordExpiryMinutes);
//        
//        // Kullanıcı verisini güncelle
//        user.setResetPasswordToken(resetToken);
//        user.setResetPasswordExpiry(expiryTime);
//        userRepository.save(user);
//        
//        // Sıfırlama e-postası gönder
//        String resetLink = "https://sas-social.com/reset-password?token=" + resetToken;
//        String emailSubject = "Şifre Sıfırlama Talebi";
//        String emailBody = "Sayın " + user.getName() + ",\n\n" +
//                          "Hesabınız için bir şifre sıfırlama talebinde bulundunuz. " +
//                          "Şifrenizi sıfırlamak için aşağıdaki bağlantıya tıklayın:\n\n" +
//                          resetLink + "\n\n" +
//                          "Bu bağlantı " + resetPasswordExpiryMinutes + " dakika içinde geçerliliğini yitirecektir.\n\n" +
//                          "Eğer bu talebi siz yapmadıysanız, bu e-postayı görmezden gelmeniz yeterlidir.\n\n" +
//                          "SAS Social Ekibi";
//        
//        try 
//        {
//            emailService.sendEmail(email, emailSubject, emailBody);
//        } 
//        catch (Exception e) 
//        {
//            throw new AuthenticationException("Şifre sıfırlama e-postası gönderilirken hata oluştu");
//        }
//    }
//    
//    /**
//     * Şifre sıfırlama işlemini tamamlar
//     * @param token Şifre sıfırlama tokeni
//     * @param newPassword Yeni şifre
//     * @param confirmPassword Yeni şifre tekrarı
//     * @throws AuthenticationException Geçersiz token veya eşleşmeyen şifreler durumunda
//     */
//    @Transactional
//    public void resetPassword(String token, String newPassword, String confirmPassword) throws AuthenticationException 
//    {
//        if (!newPassword.equals(confirmPassword)) 
//        {
//            throw new AuthenticationException("Şifreler eşleşmiyor");
//        }
//        
//        Optional<User> userOpt = userRepository.findByResetPasswordToken(token);
//        
//        if (userOpt.isEmpty()) 
//        {
//            throw new AuthenticationException("Geçersiz veya süresi dolmuş şifre sıfırlama bağlantısı");
//        }
//        
//        User user = userOpt.get();
//        
//        // Token'in süresinin dolup dolmadığını kontrol et
//        if (user.getResetPasswordExpiry().isBefore(LocalDateTime.now())) 
//        {
//            // Token'i sıfırla ve hata fırlat
//            user.setResetPasswordToken(null);
//            user.setResetPasswordExpiry(null);
//            userRepository.save(user);
//            throw new AuthenticationException("Şifre sıfırlama bağlantısının süresi dolmuş, lütfen yeni bir talepte bulunun");
//        }
//        
//        // Şifreleri güncelle ve token bilgilerini temizle
//        user.setPassword(hashPassword(newPassword));
//        user.setResetPasswordToken(null);
//        user.setResetPasswordExpiry(null);
//        userRepository.save(user);
//    }
}

//----------------------------------------------------------------------------------------------------------------