package com.example.vms.controller.auth;

import com.example.vms.app.mess.Message;
import com.example.vms.app.security.CurrentUser;
import com.example.vms.app.security.JwtAuthenticationResponse;
import com.example.vms.app.security.JwtTokenProvider;
import com.example.vms.app.service.UserService;
import com.example.vms.controller.auth.payload.LoginRequest;
import com.example.vms.controller.auth.payload.RegisterRequest;
import com.example.vms.controller.helper.CartHelper;
import com.example.vms.controller.helper.UserHelper;
import com.example.vms.data.model.CartItem;
import com.example.vms.data.model.CustomUserDetails;
import com.example.vms.data.model.User;
import com.example.vms.entity.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserHelper userHelper;
    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    private final CartHelper cartHelper;
    @Autowired
    private HttpSession httpSession;

    @GetMapping("/me")
    public ResponseEntity authenticateUser(@CurrentUser CustomUserDetails userDetails) {
        if (userDetails != null) {
            return Response.data(userDetails.getUser());
        } else {
            return Response.error(HttpStatus.UNAUTHORIZED, Message.DANG_NHAP_LAI);
        }

    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest request) {
        try {
            // Xác thực từ username và password.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            // Nếu không xảy ra exception tức là thông tin hợp lệ
            // Set thông tin authentication vào Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);


            // Trả về jwt cho người dùng.
            String jwt = tokenProvider.generateToken(authentication);
            System.out.println("authentication.getPrincipal() " + authentication.getPrincipal());
            return Response.data(new JwtAuthenticationResponse(jwt, authentication.getPrincipal()));

        } catch (BadCredentialsException e) {
            return Response.error(HttpStatus.BAD_REQUEST, "Tên đăng nhập hoặc tài khoản không chính xác.");
        }
    }


    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequest request) {
        String error = userHelper.checkRegisterUserRequest(request);

        if (StringUtils.isEmpty(error)) {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            user.setImgAvatar(request.getImgAvatar());
            user = userService.save(user);

            return Response.data(user);
        } else {
            return Response.error(HttpStatus.BAD_REQUEST, error);
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Hủy bỏ session
        }

        // Xóa dữ liệu liên quan khác (tuỳ ứng dụng của bạn)

        return Message.DANG_XUAT;
    }
    @PostMapping("/addCards")
    public String addCarts(@RequestBody List<CartItem> cartItems) {
        try {
            String user_id = (String) httpSession.getAttribute("user_id");
            if (user_id != null) {
                String username = user_id;
                cartHelper.addCartForUser(username, cartItems);

                System.out.println("codeKoCanTest " + cartItems);
                return Message.DA_THEM_VAO_GIO_HANG;
            } else {
                System.out.println("codeKoCanTest erro " + cartItems);
                return Message.VUI_LONG_DANG_NHAP;
            }
        } catch (Exception e){
            return Message.BAO_TRI + "" + Message.VUI_LONG_THU_LAI;
        }

    }

}
