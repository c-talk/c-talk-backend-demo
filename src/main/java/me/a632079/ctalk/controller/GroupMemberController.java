package me.a632079.ctalk.controller;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import me.a632079.ctalk.po.GroupMember;
import me.a632079.ctalk.repository.GroupMemberRepository;
import me.a632079.ctalk.service.GroupMemberService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @className: GroupMemberController
 * @description: GroupMemberController - TODO
 * @version: v1.0.0
 * @author: haoduor
 */

@Slf4j
@RestController
@RequestMapping("/group/member")
public class GroupMemberController {

    @Resource
    private GroupMemberService groupMemberService;

    @Resource
    private GroupMemberRepository groupMemberRepository;

    @PostMapping("/exist/{gid}")
    public void exist(@PathVariable Long gid) {
        groupMemberRepository.removeByUidAndGid(StpUtil.getLoginIdAsLong(), gid);
    }

    @GetMapping("/list/{gid}")
    public List<GroupMember> list(@PathVariable Long gid) {
        return groupMemberRepository.findAllByUidAndGid(StpUtil.getLoginIdAsLong(), gid);
    }
}
