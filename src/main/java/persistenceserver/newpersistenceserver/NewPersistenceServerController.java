package persistenceserver.newpersistenceserver;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import persistenceserver.DatabaseModels.*;
import persistenceserver.Model.Membership;
import persistenceserver.Model.User;
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

    @Autowired
    public NewPersistenceServerController(GroupRepository groupRepository, GroupMembersRepository groupMembersRepository, InvitationRepository invitationRepository, NoteRepository noteRepository, UserRepository userRepository) {
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
    public ResponseEntity<GroupModel> getGroup(@PathVariable(value = "id") long id) {
        try {
            Optional<GroupModel> groupModel = groupRepository.findById(id);
            return new ResponseEntity<>(groupModel.get(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("hey");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/group")
    public ResponseEntity<GroupModel> createGroup(@RequestBody String json) {
        try {
            GroupModel groupModelFromBody = new GroupModel(json);
            GroupModel groupModel = groupRepository.save(groupModelFromBody);
            return new ResponseEntity<>(groupModel, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("hey");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/userlist/{id}")
    public ResponseEntity<List<Membership>> getUserList(@PathVariable(value = "id") int id) {
        try {
            List<GroupMembersModel> groupMembersModelList = groupMembersRepository.findByGroupId(id);
            List<Membership> membershipList = groupMembersModelList.stream().map(a ->
                    new Membership(a.getId(), a.getUserModel().getId(), a.getGroupId(), a.getUserModel().getUsername())).collect(Collectors.toList());
            return new ResponseEntity<>(membershipList, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("hey");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user")
    public ResponseEntity<UserModel> ValidateUser(@RequestBody String json) {
        try {
            Optional<UserModel> userModel = userRepository.findByUsername(json);
            return new ResponseEntity<>(userModel.get(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("hey");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/unregisteruser")
    public ResponseEntity<UserModel> registerUser(@RequestBody String json) {
        try {
            UserModel userModelFromBody = gson.fromJson(json, UserModel.class);
            UserModel userModel = userRepository.save(userModelFromBody);
            return new ResponseEntity<>(userModel, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("hey");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/note")
    public ResponseEntity<NoteModel> addNote(@RequestBody String json) {
        System.out.println("It's working AddNote");
        try {
            NoteModel noteModelFromBody = gson.fromJson(json, NoteModel.class);
            NoteModel noteModel = noteRepository.save(noteModelFromBody);
            return new ResponseEntity<>(noteModel, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("hey");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/note")
    public ResponseEntity<NoteModel> editNote(@RequestBody String json) {
        System.out.println("EDIT BABYYY");
        try {
            NoteModel noteModelFromBody = gson.fromJson(json, NoteModel.class);
            NoteModel noteModel = noteRepository.save(noteModelFromBody);
            return new ResponseEntity<>(noteModel, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("hey");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/note/{groupId}")
    public ResponseEntity<List<NoteModel>> getNote(@PathVariable(value = "groupId") long groupId) {
        try {
            List<NoteModel> noteModelList = noteRepository.findByGroupModel_Id(groupId);
            return new ResponseEntity<>(noteModelList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/invitation")
    public ResponseEntity<InvitationModel> addInvitation(@RequestBody String json) {
        System.out.println("its working post invitation");
        try {
            InvitationModel invitationModelFromBody = gson.fromJson(json, InvitationModel.class);
            InvitationModel invitationModel = invitationRepository.save(invitationModelFromBody);
            return new ResponseEntity<>(invitationModel, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("hey");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/invitation/{id}")
    public ResponseEntity<List<InvitationModel>> getInvitationList(@PathVariable(value = "id") long id) {
        System.out.println("Aleeeoooo");
        try {
            List<InvitationModel> invitationModelList = invitationRepository.findByInvitee_Id(id);
            return new ResponseEntity<>(invitationModelList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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


    @PostMapping("/user/{user_id}")
    public ResponseEntity<UserModel> EditUser(@RequestBody String json, @PathVariable(value = "user_id") int user_id) {

        System.out.println("It's working Validate");
        try {
            UserModel temp = gson.fromJson(json, UserModel.class);
            UserModel userModel = userRepository.save(temp);
            return new ResponseEntity<>(userModel, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("hey");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
