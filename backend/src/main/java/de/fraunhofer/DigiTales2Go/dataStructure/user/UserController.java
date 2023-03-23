/**
 * @author Markus Walter
 */
package de.fraunhofer.DigiTales2Go.dataStructure.user;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Industry;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Role;
import de.fraunhofer.DigiTales2Go.dataStructure.linkingServices.ratingLinkServices.RatingLinkRemover;
import de.fraunhofer.DigiTales2Go.dataStructure.rating.RatingRepository;
import de.fraunhofer.DigiTales2Go.dataStructure.user.dtos.*;
import de.fraunhofer.DigiTales2Go.feed.ItemLibrary;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.Pageable.Pageable;
import de.fraunhofer.DigiTales2Go.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.*;

/**
 * The type AppUser controller.
 *
 * @author Markus Walter
 */
@RestController
@CrossOrigin
@Slf4j
public class UserController {
    /**
     * The User rep.
     */
    @Autowired
    UserRepository userRep;
    /**
     * The Password encoder.
     */
    @Autowired
    PasswordEncoder passwordEncoder;
    /**
     * The Authentication manager.
     */
    @Autowired
    AuthenticationManager authenticationManager;
    /**
     * The Jwt token provider.
     */
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    /**
     * The Rating link remover.
     */
    @Autowired
    RatingLinkRemover ratingLinkRemover;
    /**
     * The Rating repository.
     */
    @Autowired
    RatingRepository ratingRepository;
    /**
     * The Item library.
     */
    @Autowired
    ItemLibrary itemLibrary;
    /**
     * The User service.
     */
    @Autowired
    UserService userService;


    @Value("${admin.firstName}")
    private String adminFirstName;
    @Value("${admin.lastName}")
    private String adminLastName;
    @Value("${admin.mail}")
    private String adminMail;
    @Value("${admin.company}")
    private String adminCompany;
    @Value("${admin.password}")
    private String adminPassword;
    @Value("${admin.job}")
    private String adminJob;
    @Value("${admin.mandate}")
    private String adminMandate;
    @Value("${mail.configured}")
    private boolean mailServiceAvailable;

    /**
     * The Role admin.
     */
    String roleAdmin = "ROLE_ADMIN";

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        Optional<AppUser> admin = userRep.findByEmail(adminMail);
        if (admin.isEmpty()) {
            AppUser defaultAdmin = new AppUser(
                    null,
                    adminFirstName,
                    adminLastName,
                    adminMail,
                    adminCompany,
                    adminJob,
                    Industry.SOFTWARE.getIndustryName(),
                    adminMandate,
                    passwordEncoder.encode(adminPassword),
                    Role.ROLE_ADMIN,
                    new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            userRep.save(defaultAdmin);
        }
    }

    /**
     * Gets user.
     *
     * @param id           the id
     * @param loggedInUser the logged in user
     * @return the user
     */
    @GetMapping("/user/{id}")
    public AppUser getUser(@PathVariable("id") long id, @AuthenticationPrincipal User loggedInUser) {
        //loggedInUser cannot be null due to Spring Security constraint
        Optional<AppUser> foundAppUser = userRep.findById(id);
        if (foundAppUser.isPresent()
                && (loggedInUser.getUsername().equals(foundAppUser.get().getEmail())
                    || loggedInUser.getAuthorities().contains(new SimpleGrantedAuthority(roleAdmin)))) {
                return foundAppUser.get();

        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("AppUser with Id %s not found", id));
    }

    /**
     * Gets user priorRating.
     *
     * @param loggedInUser the logged in user
     * @return the user priorRating
     */
    @PostMapping("/user/ratings/")
    public RatingFeedDTO getUserRating(@AuthenticationPrincipal User loggedInUser, @Valid @RequestBody Pageable pageable) {
        //loggedInUser cannot be null due to Spring Security constraint
        Optional<AppUser> foundAppUser = userRep.findByEmail(loggedInUser.getUsername());
        if (foundAppUser.isPresent()) {
            log.debug("User found");
            return userService.paginateRatings(foundAppUser.get().getRatings(), pageable, foundAppUser.get());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    /**
     * Gets all users.
     *
     * @return the user
     */
    @GetMapping("/user/")
    public List<AppUser> getUsers() {
        return (List<AppUser>) userRep.findAll();
    }

    /**
     * Create user.
     *
     * @param requestBody the request body
     * @return the user
     */
    @PostMapping("/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AppUser> createUser(@Valid @RequestBody AppUserCreationDTO requestBody) {
        Optional<AppUser> userOptional = userRep.findByEmail(requestBody.getEmail());
        log.info("Creating new user");
        if (!userOptional.isPresent()) {
            AppUser user = new AppUser(requestBody);
            user.setPassword(passwordEncoder.encode(requestBody.getPassword()));
            AppUser savedUser = userRep.save(user);
            if (mailServiceAvailable){
                userService.sendMail(requestBody.getEmail(), "Ihr Konto wurde erfolgreich angelegt.");
            }
            log.info("Saving new User: {}", savedUser.getEmail());


            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Login response entity.
     *
     * @param authRequest the auth request
     * @return the response entity
     */
    @PostMapping("/auth/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<LoginReturnDTO> login(@RequestBody AuthRequestDTO authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );
        LoginReturnDTO loginReturnDTO = new LoginReturnDTO();
        Optional<AppUser> userOptional = userRep.findByEmail(authRequest.getEmail());
        if (userOptional.isPresent()) {
            loginReturnDTO.setId(userOptional.get().getId());
            loginReturnDTO.setToken(jwtTokenProvider.generateToken(authentication));
            log.info("Logged in user: {}", userOptional.get().getEmail());
        }
        return ResponseEntity.ok(loginReturnDTO);
    }

    /**
     * Update user.
     *
     * @param id           the id
     * @param requestBody  the request body
     * @param loggedInUser the logged in user
     * @return the user
     */
    @PutMapping("/user/{id}")
    public ResponseEntity<AppUser> updateUser(@PathVariable("id") Long id, @Valid @RequestBody AppUserDataChangeDTO requestBody, @AuthenticationPrincipal User loggedInUser) {
        //loggedInUser cannot be null due to Spring Security constraint
        Optional<AppUser> appUserToUpdate = userRep.findById(id);
        if (appUserToUpdate.isPresent()) {
            if (loggedInUser.getUsername().equals(appUserToUpdate.get().getEmail())
                    || loggedInUser.getAuthorities().contains(new SimpleGrantedAuthority(roleAdmin))) {
                if (mailServiceAvailable){
                    userService.sendMail(requestBody.getEmail(), "Ihre Konto-Einstellungen wurden Erfolgreich geändert.");
                }
                return ResponseEntity.ok(userService.updateUser(appUserToUpdate.get(), requestBody));
            }
            log.error("user is neither the right user nor admin");
        }
        log.error("AppUser with id {} not found!", id);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("AppUser with id %s not found!", id));
    }

    /**
     * Update user role response entity.
     *
     * @param id          the id
     * @param requestBody the request body
     * @return the response entity
     */
    @PutMapping("/user/role/{id}") //Only doable by Admin, so no further check needed
    public ResponseEntity<AppUser> updateUserRole(@PathVariable("id") Long id, @RequestBody RoleChangeDTO requestBody) {
        Optional<AppUser> appUserToUpdate = userRep.findById(id);
        if (appUserToUpdate.isPresent()) {
            appUserToUpdate.get().setRole(requestBody.getRole());
            log.info("Updating Role of User {} to {}", appUserToUpdate.get().getEmail(), requestBody);
            AppUser updatedUser = userRep.save(appUserToUpdate.get());
            return ResponseEntity.ok(updatedUser);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("AppUser with id %s not found!", id));
    }

    /**
     * Delete user.
     *
     * @param id           the id
     * @param loggedInUser the logged in user
     */
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id") long id, @AuthenticationPrincipal User loggedInUser) {
        Optional<AppUser> user = userRep.findById(id);
        log.info("deleting user");
        if (user.isPresent()

                && (loggedInUser.getUsername().equals(user.get().getEmail())
                    || loggedInUser.getAuthorities().contains(new SimpleGrantedAuthority(roleAdmin)))) {
                log.info("User found in DB, and has right to delete");
                userService.deleteUser(user.get());
                return;
            }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("AppUser with Id %s not found!", id));
    }
}
