package com.ngtesting.platform.action.client;

import com.alibaba.fastjson.JSONObject;
import com.ngtesting.platform.action.BaseAction;
import com.ngtesting.platform.config.Constant;
import com.ngtesting.platform.model.IsuComments;
import com.ngtesting.platform.model.TstUser;
import com.ngtesting.platform.service.intf.IssueCommentsService;
import com.ngtesting.platform.servlet.PrivPrj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(Constant.API_PATH_CLIENT + "issue_comments/")
public class IssueCommentsAction extends BaseAction {
    @Autowired
    IssueCommentsService issueCommentsService;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    @PrivPrj(perms = {"issue-maintain"})
    public Map<String, Object> save(HttpServletRequest request, @RequestBody JSONObject json) {
        Map<String, Object> ret = new HashMap<String, Object>();

        TstUser user = (TstUser) request.getSession().getAttribute(Constant.HTTP_SESSION_USER_PROFILE);

        IsuComments vo = issueCommentsService.save(json, user);

        if (vo == null) {
            return authFail();
        }

        ret.put("data", vo);
        ret.put("code", Constant.RespCode.SUCCESS.getCode());
        return ret;
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    @PrivPrj(perms = {"issue-maintain"})
    public Map<String, Object> delete(HttpServletRequest request, @RequestBody JSONObject json) {
        Map<String, Object> ret = new HashMap<String, Object>();

        TstUser user = (TstUser) request.getSession().getAttribute(Constant.HTTP_SESSION_USER_PROFILE);

        Integer id = json.getInteger("id");

        Boolean result = issueCommentsService.delete(id, user);
        if (!result) {
            return authFail();
        }

        ret.put("code", Constant.RespCode.SUCCESS.getCode());
        return ret;
    }
}
