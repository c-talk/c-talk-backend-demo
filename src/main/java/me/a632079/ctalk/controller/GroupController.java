package me.a632079.ctalk.controller;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import me.a632079.ctalk.po.Group;
import me.a632079.ctalk.po.GroupMember;
import me.a632079.ctalk.repository.GroupRepository;
import me.a632079.ctalk.service.GroupMemberService;
import me.a632079.ctalk.service.GroupService;
import me.a632079.ctalk.vo.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @className: GroupController
 * @description: GroupController - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Slf4j
@RestController
@RequestMapping("/group")
public class GroupController {

    @Resource
    private GroupService groupService;

    @Resource
    private GroupRepository groupRepository;

    @Resource
    private GroupMemberService groupMemberService;

    @PostMapping("/create")
    public Group createGroup(@RequestBody GroupForm groupForm) {
        return groupService.createGroup(groupForm);
    }

    @PostMapping("/join")
    public Group joinGroup(@RequestBody IdForm form) {
        Long uid = StpUtil.getLoginIdAsLong();

        Group group = groupRepository.findFirstById(form.getId());

        if (Objects.isNull(group)) {
            return null;
        }

        groupMemberService.addGroupMember(group.getId(), uid);

        return group;
    }

    @GetMapping("/invite/{code}")
    public GroupVo invite(@PathVariable String code) {
        Group group = groupRepository.findFirstByCode(code);
        List<GroupMember> memberList = groupMemberService.listMember(group.getId(), 5);
        GroupVo groupVo = new GroupVo();
        groupVo.setGroup(group);
        groupVo.setMemberList(memberList);
        return groupVo;
    }

    @PostMapping("/page")
    public PageVo<Group> pageGroup(@RequestBody GroupPageForm form) {
        return groupService.pageGroup(form);
    }

    @PostMapping("/set")
    public Group setGroup(@RequestBody GroupSetForm form) {
        return groupService.setGroup(form);
    }

    @PostMapping("/remove")
    public void removeGroup(@RequestBody IdForm form) {
        groupService.deleteGroup(form.getId());
    }
}
