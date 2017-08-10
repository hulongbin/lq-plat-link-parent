package com.lq.plat.link.controller.portal;

import com.lq.plat.link.Controller.PlatformController;
import com.lq.plat.link.PlatformResult;
import com.lq.plat.link.knowledage.KnowledgeQuestionUpdatePara;
import com.lq.plat.link.service.KnowledgeAnswerService;
import com.lq.plat.link.service.KnowledgeQuestionService;
import com.lq.plat.link.utils.ApiUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.validation.Valid;
import java.util.concurrent.Callable;

/**
 * 后台：知识相关
 * @author 李强
 * @version 1.0.0
 * @date 2017/8/9
 */
public class KnowledgeController  extends PlatformController {

    @Autowired
    private KnowledgeQuestionService knowledgeQuestionService;

    @Autowired
    private KnowledgeAnswerService knowledgeAnswerService;



    //当确认收到钱之后，后台将该问题状态设为已发布
    @ApiOperation(value = "更新答案信息", notes = "添加返回的结果")
    @PutMapping("manage/knowledgequestions")
    public WebAsyncTask<ResponseEntity<PlatformResult>> updateInfoAccounts(
            @Valid @RequestBody final KnowledgeQuestionUpdatePara knowledgeQuestionUpdatePara, final BindingResult result) {

        Callable<ResponseEntity<PlatformResult>> callable =
                new Callable<ResponseEntity<PlatformResult>>() {

                    @Override
                    public ResponseEntity<PlatformResult> call() throws Exception {

                        return ApiUtils.ok(knowledgeQuestionService.update(knowledgeQuestionUpdatePara));
                    }
                };

        return getWebAsyncTask(callable);
    }

    //查询最佳回答者
    @ApiOperation(value = "查询最佳回答者", notes = "查询返回的结果")
    @GetMapping("manage/knowledgeanswers/bestAnswers")
    public WebAsyncTask<ResponseEntity<PlatformResult>> getBestAnswers( @PageableDefault final Pageable pageable) {

        Callable<ResponseEntity<PlatformResult>> callable =
                new Callable<ResponseEntity<PlatformResult>>() {
                    @Override
                    public ResponseEntity<PlatformResult> call() throws Exception {
                        return ApiUtils.ok(knowledgeAnswerService.findBestAnswer(pageable));
                    }
                };
        return getWebAsyncTask(callable);
    }


}