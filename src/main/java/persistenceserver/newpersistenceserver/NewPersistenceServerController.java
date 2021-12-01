package persistenceserver.newpersistenceserver;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import persistenceserver.DatabaseModels.*;
import persistenceserver.Model.*;
import persistenceserver.jparepositories.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class NewPersistenceServerController {

    private static final Gson gson = new Gson();

    private final GroupRepository groupRepository;
    private final GroupMembersRepository groupMembersRepository;
    private final InvitationRepository invitationRepository;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Autowired
    public NewPersistenceServerController(GroupRepository groupRepository, GroupMembersRepository groupMembersRepository,
                                          InvitationRepository invitationRepository, NoteRepository noteRepository,
                                          UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.groupMembersRepository = groupMembersRepository;
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.noteRepository = noteRepository;
        System.out.println("Hey");
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

    @PostMapping("/user")
    public ResponseEntity<UserModel> ValidateUser(@RequestBody String json) {
        try {
            User userModelFromBody = gson.fromJson(json, User.class);
            System.out.println(json);
            Optional<UserModel> userModel = userRepository.findByUsername(userModelFromBody.getUsername());
            return new ResponseEntity<>(userModel.get(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("hey");
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

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "userId") long userId) {
        try {
            userRepository.deleteById(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/note")
    public ResponseEntity<Note> addNote(@RequestBody String json) {
        System.out.println("It's working AddNote");
        try {
            Note noteFromBody = gson.fromJson(json, Note.class);
            System.out.println(json);
            GroupModel groupModel = groupRepository.getById(noteFromBody.getGroupId());
            NoteModel noteModelFromBody = new NoteModel(noteFromBody.getUserId(), groupModel, noteFromBody.getWeek(),
                    noteFromBody.getYear(), noteFromBody.getName(), noteFromBody.getStatus(), noteFromBody.getText());
            NoteModel noteModel = noteRepository.save(noteModelFromBody);

            Note noteFinal = new Note(noteModel.getId(),noteModel.getUser_id(),noteModel.getGroupModel().getId(),
                    noteModel.getWeek(),noteModel.getYear(),noteModel.getName(),noteModel.getStatus(),noteModel.getText());
            return new ResponseEntity<>(noteFinal, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("hey");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/note/{id}")
    public ResponseEntity<NoteModel> getOneNote(@PathVariable(value = "id") long id ){
        System.out.println("It's working AddNote");
        try {
            Optional<NoteModel> noteModel = noteRepository.findById(id);
            return new ResponseEntity<>(noteModel.get(), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("hey");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/note")
    ResponseEntity<NoteModel> editNote(@RequestBody String json) {
        System.out.println("It's working EditNote");
        try {
            NoteModel noteModelFromBody = gson.fromJson(json, NoteModel.class);
            NoteModel noteModel = noteRepository.save(noteModelFromBody);
            return new ResponseEntity<>(noteModel, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("hey");
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

//    @GetMapping("/note/{groupId}")
//    public ResponseEntity<List<NoteModel>> getNote(@PathVariable(value = "groupId") long groupId) {
//        try {
//            List<NoteModel> noteModelList = noteRepository.findByGroupModel_IdOrderByWeek(groupId);
//            return new ResponseEntity<>(noteModelList, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

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

    @GetMapping("/userlist/{id}")
    public ResponseEntity<List<Membership>> getUserList(@PathVariable(value = "id") long id) {
        try {
            System.out.println("userList" + id);
            List<GroupMembersModel> groupMembersModelList = groupMembersRepository.findByGroupModel_Id(id);
            List<Membership> membershipList = groupMembersModelList.stream().map(a ->
                    new Membership(a.getId(), a.getUserModel().getId(), a.getGroupModel().getId(), a.getUserModel().getUsername())).collect(Collectors.toList());

            return new ResponseEntity<>(membershipList, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("hey");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/groupmemberslist/{id}")
    public ResponseEntity<List<Group>> getMembershipList(@PathVariable(value = "id") long id) {
        try {
            System.out.println("id" + id);
            List<GroupMembersModel> groupModelList = groupMembersRepository.findByUserModel_Id(id);
            System.out.println(groupModelList.size());
            List<Group> membershipList = groupModelList.stream().map(a ->
                    new Group(a.getGroupModel().getId(), a.getGroupModel().getName())).collect(Collectors.toList());
            return new ResponseEntity<>(membershipList, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("hey");
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

    @PostMapping("/invitation")
    public ResponseEntity<Invitation> addInvitation(@RequestBody String json) {
        System.out.println("its working post invitation");
        try {
            Invitation invitationFromBody = gson.fromJson(json, Invitation.class);
            System.out.println(json);
            UserModel invitor = userRepository.getById(invitationFromBody.getInvitorId());
            UserModel invitee = userRepository.getById(invitationFromBody.getInviteeId());
            GroupModel groupModel = groupRepository.getById(invitationFromBody.getGroupId());
            InvitationModel invitationModel = invitationRepository.save(new InvitationModel(invitor, invitee, groupModel));
            Invitation invitationFinal = new Invitation(invitationModel.getId(), invitationModel.getInvitor().getId(),
                    invitationModel.getInvitee().getId(), invitationModel.getGroupModel().getId());
            return new ResponseEntity<>(invitationFinal, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("hey");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/invitation/{id}")
    public ResponseEntity<Void> deleteInvitation(@PathVariable(value = "id") long id) {
        try {
            System.out.println(id);
            invitationRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/invitation/{id}")
    public ResponseEntity<List<Invitation>> getInvitationList(@PathVariable(value = "id") long id) {
        System.out.println("Aleeeoooo");
        try {
            List<InvitationModel> invitationModelList = invitationRepository.findByInvitee_Id(id);
            List<Invitation> invitationList = invitationModelList.stream().map(a ->
                    new Invitation(a.getId(), a.getInvitor().getId(), a.getInvitee().getId(), a.getGroupModel().getId())).collect(Collectors.toList());

            return new ResponseEntity<>(invitationList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/groupmembers")
    ResponseEntity<Membership> AddGroupMember(@RequestBody String json) {
        System.out.println("its working post groupMembers");
        try {
            Membership membershipModelFromBody = gson.fromJson(json, Membership.class);
            Optional<UserModel> userModel = userRepository.findById(membershipModelFromBody.getUserId());
            Optional<GroupModel> groupModel = groupRepository.findById(membershipModelFromBody.getGroupId());
            GroupMembersModel groupMembersModel = new GroupMembersModel(userModel.get(), groupModel.get());
            GroupMembersModel groupMembersModelFinal = groupMembersRepository.save(groupMembersModel);
            Membership membership = new Membership(groupMembersModelFinal.getId(), groupMembersModelFinal.getUserModel().getId(), groupMembersModelFinal.getGroupModel().getId(), null);
            return new ResponseEntity<>(membership, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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

}
