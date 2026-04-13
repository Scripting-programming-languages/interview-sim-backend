package com.github.scripting.programming.language.interview_sim_backend.service.impl;

import com.github.scripting.programming.language.grpc.NextQuestionRequest;
import com.github.scripting.programming.language.grpc.QuestionChunk;
import com.github.scripting.programming.language.grpc.QuestionData;
import com.github.scripting.programming.language.grpc.QuestionStreamServiceGrpc;
import com.github.scripting.programming.language.interview_sim_backend.config.filter.JwtServerInterceptor;
import com.github.scripting.programming.language.interview_sim_backend.service.AttemptService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@GrpcService(interceptors = {JwtServerInterceptor.class})
public class QuestionDatabaseGeneratorStreamService extends QuestionStreamServiceGrpc.QuestionStreamServiceImplBase {
    private final AttemptService attemptService;

    @Override
    public void streamNextQuestion(NextQuestionRequest request, StreamObserver<QuestionChunk> responseObserver) {
        Long userIdFromToken = JwtServerInterceptor.USER_ID_CONTEXT_KEY.get();
        if (userIdFromToken == null || !userIdFromToken.equals(request.getUserId())) {
            responseObserver.onError(Status.PERMISSION_DENIED
                    .withDescription("User ID mismatch")
                    .asRuntimeException());
            return;
        }

        try {
            var nextQuestionOptional = attemptService.getNextQuestion(request);

            if (nextQuestionOptional.isEmpty()) {
                responseObserver.onCompleted();
                return;
            }

            String text = nextQuestionOptional.get().getText();
            String[] words = text.split(" ");

            for (String word : words) {
                QuestionChunk chunk = QuestionChunk.newBuilder()
                        .setTextDelta(word + " ")
                        .build();
                responseObserver.onNext(chunk);

                // Небольшая задержка для имитации генерации
                Thread.sleep(150);
            }

            QuestionData finalData = QuestionData.newBuilder()
                    .setQuestionId(nextQuestionOptional.get().getId())
                    .setFullText(text)
                    .setComplexity(nextQuestionOptional.get().getComplexity())
                    .build();

            responseObserver.onNext(QuestionChunk.newBuilder()
                    .setFinalData(finalData)
                    .build());
            responseObserver.onCompleted();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            responseObserver.onError(Status.CANCELLED.withDescription("Stream interrupted").asException());
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asException());
        }
    }


}
