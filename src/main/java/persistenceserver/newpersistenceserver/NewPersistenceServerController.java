package persistenceserver.newpersistenceserver;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import persistenceserver.DatabaseModels.*;
import persistenceserver.Model.Membership;
import persistenceserver.Services.PersistenceService;
import persistenceserver.jparepositories.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class NewPersistenceServerController {

    private static final Gson gson = new Gson();

    private GroupRepository groupRepository;
    private GroupMembersRepository groupMembersRepository;
    private InvitationRepository invitationRepository;
    private NoteRepository noteRepository;
    private UserRepository userRepository;

    private PersistenceService persistenceService;


    @Autowired
    public NewPersistenceServerController(GroupRepository groupRepository, GroupMembersRepository groupMembersRepository, InvitationRepository invitationRepository, NoteRepository noteRepository, UserRepository userRepository) {
        this.persistenceService = new PersistenceService();
        this.groupRepository = groupRepository;
        this.groupMembersRepository = groupMembersRepository;
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
    }

    @GetMapping("/groups")
    public ResponseEntity<List<GroupModel>> getGroups() {
        try {
            List<GroupModel> groupModelList = groupRepository.findAll();
            return new ResponseEntity<>(groupModelList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/group/{id}")
    public Optional<GroupModel> getGroup(@PathVariable(value = "id") long id) {
        return groupRepository.findById(id);
    }

    @PutMapping("/group")
    public GroupModel createGroup(@RequestBody String json) {
        GroupModel groupModel = new GroupModel(json);
        return groupRepository.save(groupModel);
    }

    @GetMapping("/userlist/{id}")
    public List<Membership> getUserList(@PathVariable(value = "id") int id) {
        List<GroupMembersModel> groupMembersModelList = groupMembersRepository.findByGroupId(id);
        List<Membership> membershipList = groupMembersModelList.stream().map(a ->
                new Membership(a.getId(), a.getUserModel().getId(), a.getGroupId(), a.getUserModel().getUsername())).collect(Collectors.toList());
        return membershipList;
    }


    @PostMapping("/user")
    public Optional<UserModel> ValidateUser(@RequestBody String json) {
        return userRepository.findByUsername(json);
    }


    @PostMapping("/unregisteruser")
    public UserModel registerUser(@RequestBody String json) {
        UserModel userModel = gson.fromJson(json, UserModel.class);
        return userRepository.save(userModel);
    }


    @PostMapping("/note")
    public NoteModel addNote(@RequestBody String json) {
        System.out.println("It's working AddNote");
        NoteModel noteModel = gson.fromJson(json, NoteModel.class);
        return noteRepository.save(noteModel);
    }

    @PutMapping("/note")
    public NoteModel editNote(@RequestBody String json) {
        System.out.println("EDIT BABYYY");
        NoteModel noteModel = gson.fromJson(json, NoteModel.class);
        return noteRepository.save(noteModel);
    }


    @GetMapping("/note/{groupId}")
    public List<NoteModel> getNote(@PathVariable(value = "groupId") long groupId) {
        return noteRepository.findByGroupModel_Id(groupId);
    }

    @PostMapping("/invitation")
    public InvitationModel addInvitation(@RequestBody String json) {
        System.out.println("its working post invitation");
        InvitationModel invitationModel = gson.fromJson(json, InvitationModel.class);
        return invitationRepository.save(invitationModel);
    }

    @GetMapping("/invitation/{id}")
    public List<InvitationModel> getInvitationList(@PathVariable(value = "id") long id) {
        System.out.println("Aleeeoooo");
        return invitationRepository.findByInvitee_Id(id);
    }


    @DeleteMapping("/invitation/{id}")
    public ResponseEntity<Void> deleteInvitation(@PathVariable(value = "id") long id) {
        try {
            invitationRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/note/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable(value = "noteId") long noteId) {
        try {
            noteRepository.deleteById(noteId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/group/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable(value = "id") long id) {
        try {
            groupRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "userId") long userId) {
        try {
            userRepository.deleteById(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


//    @PostMapping("/user/{user_id}")
//    public synchronized String EditUser(@RequestBody String json, @PathVariable(value = "user_id") int user_id) {
//        System.out.println("It's working Validate");
//        return persistenceService.editUser(json, user_id);
//    }
}
