package edu.ucla.cs.er.depressionstudy

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.quickbirdstudios.surveykit.*
import com.quickbirdstudios.surveykit.result.TaskResult
import com.quickbirdstudios.surveykit.steps.CompletionStep
import com.quickbirdstudios.surveykit.steps.InstructionStep
import com.quickbirdstudios.surveykit.steps.QuestionStep
import com.quickbirdstudios.surveykit.survey.SurveyView

class SurveyFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_survey, container, false)

        var surveyView: SurveyView = rootView!!.findViewById(R.id.survey_view)

        val instructions = InstructionStep(
                title = "Survey",
                text = "You can start the survey by pressing the button below.",
                buttonText = "Start"
        )

        val age = QuestionStep(
                title = "Age",
                text = "How old are you today?",
                answerFormat = AnswerFormat.IntegerAnswerFormat()
        )

        val substanceUse = QuestionStep(
                title = "Substance Use",
                text = "In the past 3 months, have you used substances such as crystal meth or injectable drugs not prescribed to you by a physician?",
                answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                        textChoices = listOf(TextChoice("Yes, methamphetamines"),
                                TextChoice("Yes, injectable drugs not prescribed"),
                                TextChoice("Yes, used both"),
                                TextChoice("Neither"))
                )
        )

        val pwid1 = QuestionStep(
                title = "HIV Risk",
                text = "In the past 3 months, were you in a methadone maintenance program?",
                answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                        textChoices = listOf(TextChoice("Yes"),
                                TextChoice("No"))
                )
        )

        val pwid2 = QuestionStep(
                title = "HIV Risk",
                text = "In the past 3 months, did you inject cocaine?",
                answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                        textChoices = listOf(TextChoice("Yes"),
                                TextChoice("No"))
                )
        )
        val pwid3 = QuestionStep(
                title = "HIV Risk",
                text = "In the past 3 months, did you inject heroin?",
                answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                        textChoices = listOf(TextChoice("Yes"),
                                TextChoice("No"))
                )
        )
        val pwid4 = QuestionStep(
                title = "HIV Risk",
                text = "In the past 3 months, did you use needles?",
                answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                        textChoices = listOf(TextChoice("Yes"),
                                TextChoice("No"))
                )
        )
        val pwid5 = QuestionStep(
                title = "HIV Risk",
                text = "In the past 3 months, did you share a cooker?",
                answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                        textChoices = listOf(TextChoice("Yes"),
                                TextChoice("No"))
                )
        )
        val pwid6 = QuestionStep(
                title = "HIV Risk",
                text = "In the past 3 months, did you visit a shooting gallery?",
                answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                        textChoices = listOf(TextChoice("Yes"),
                                TextChoice("No"))
                )
        )

        val sexBehavior1 = QuestionStep(
                title = "Sexual Behavior and Risk",
                text = "In the last 3 months, how many men have you had sex with?",
                answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                        textChoices = listOf(TextChoice(">10"),
                                TextChoice("6-10"),
                                TextChoice("1-5"),
                                TextChoice("0"))
                )
        )

        val sexBehavior2 = QuestionStep(
                title = "Sexual Behavior and Risk",
                text = "In the last 3 months, how many times did you have receptive anal sex (you were the bottom) with a man when he did not use a condom?",
                answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                        textChoices = listOf(TextChoice("1 or more times"),
                                TextChoice("0 times"))
                )
        )

        val sexBehavior3 = QuestionStep(
                title = "Sexual Behavior and Risk",
                text = "In the last 3 months, how many of your male sex partners were HIV-positive?",
                answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                        textChoices = listOf(TextChoice("More than 1 HIV+ male partners"),
                                TextChoice("1 HIV+ male partner"),
                                TextChoice("0"),
                                TextChoice("Don't know"))
                )
        )

        val sexBehavior4 = QuestionStep(
                title = "Sexual Behavior and Risk",
                text = "In the last 3 months, how many times did you have insertive anal sex (you were the top) with a man who was HIV-positive when you did not use a condom?",
                answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                        textChoices = listOf(TextChoice("5 or more times"),
                                TextChoice("0-4 times"))
                )
        )

        val finished = CompletionStep(
                title = "Survey finished",
                text = "Thank you for filling the survey! Please press the button below to submit the answers and return to the main page.",
                buttonText = "Submit"
        )

        val steps = listOf(instructions, age, substanceUse, pwid1, pwid2, pwid3, pwid4, pwid5, pwid6, sexBehavior1, sexBehavior2, sexBehavior3, sexBehavior4, finished)
        val task = NavigableOrderedTask(steps = steps)

        task.setNavigationRule(
                steps[steps.indexOf(substanceUse)].id,
                NavigationRule.ConditionalDirectionStepNavigationRule(
                        resultToStepIdentifierMapper = { input ->
                            when (input) {
                                "Yes, methamphetamines" -> steps[steps.indexOf(sexBehavior1)].id
                                "Neither" -> steps[steps.indexOf(sexBehavior1)].id
                                else -> steps[steps.indexOf(pwid1)].id
                            }
                        }
                )
        )

        task.setNavigationRule(
                steps[steps.indexOf(sexBehavior1)].id,
                NavigationRule.ConditionalDirectionStepNavigationRule(
                        resultToStepIdentifierMapper = { input ->
                            when (input) {
                                "0" -> steps[steps.indexOf(finished)].id
                                else -> steps[steps.indexOf(sexBehavior2)].id
                            }
                        }
                )
        )

        task.setNavigationRule(
                steps[steps.indexOf(sexBehavior3)].id,
                NavigationRule.ConditionalDirectionStepNavigationRule(
                        resultToStepIdentifierMapper = { input ->
                            when (input) {
                                "0" -> steps[steps.indexOf(finished)].id
                                "Don't know" -> steps[steps.indexOf(finished)].id
                                else -> steps[steps.indexOf(sexBehavior4)].id
                            }
                        }
                )
        )

        val configuration = SurveyTheme(
                themeColorDark = ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark),
                themeColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary),
                textColor = ContextCompat.getColor(requireContext(), R.color.black)
        )

        surveyView?.onSurveyFinish = { taskResult: TaskResult, reason: FinishReason ->
            if (reason == FinishReason.Completed) {
                var totalScore = 0
                val results = taskResult.results.map { res -> res.results.first().stringIdentifier }

                if (results[2].equals("Yes, methamphetamines")
                        || results[2].equals("Yes, injectable drugs not prescribed")
                        || results[2].equals("Yes, used both")) {
                    totalScore += 6
                }

                var nextIndex = 3
                if (results[2].equals("Yes, injectable drugs not prescribed")
                        || results[2].equals("Yes, used both")) {
                    var age = results[1].toInt()
                    if (age < 30) {
                        totalScore += 38
                    } else if (age < 40) {
                        totalScore += 24
                    } else if (age < 50) {
                        totalScore += 7
                    } else {
                        totalScore += 2
                    }

                    if (results[3] == "No") {
                        totalScore += 31
                    }

                    var pwidScore = 0
                    if (results[4] == "Yes") {
                        pwidScore += 1
                    }
                    if (results[5] == "Yes") {
                        pwidScore += 1
                    }
                    if (results[6] == "Yes") {
                        pwidScore += 1
                    }
                    if (results[7] == "Yes") {
                        pwidScore += 1
                    }
                    if (results[8] == "Yes") {
                        pwidScore += 1
                    }

                    if (pwidScore == 1) {
                        totalScore += 7
                    } else if (pwidScore == 2) {
                        totalScore += 21
                    } else if (pwidScore == 3) {
                        totalScore += 24
                    } else if (pwidScore == 4) {
                        totalScore += 24
                    } else if (pwidScore == 5) {
                        totalScore += 31
                    }

                    nextIndex = 9
                } else {
                    var age = results[1].toInt()
                    if (age < 18) {
                        totalScore += 0
                    } else if (age < 29) {
                        totalScore += 8
                    } else if (age < 41) {
                        totalScore += 5
                    } else if (age < 49) {
                        totalScore += 2
                    }
                }

                if (results[nextIndex] == "0") {
                    nextIndex = -1
                } else if (results[nextIndex] == "1-5") {
                    nextIndex += 1
                } else if (results[nextIndex] == "6-10") {
                    nextIndex += 1
                    totalScore += 4
                } else if (results[nextIndex] == ">10") {
                    nextIndex += 1
                    totalScore += 7
                }

                if (nextIndex > 0) {
                    if (results[nextIndex] == "1 or more times") {
                        totalScore += 10
                    }
                    nextIndex += 1

                    if (results[nextIndex] == "More than 1 HIV+ male partners") {
                        totalScore += 8
                        nextIndex += 1
                    } else if (results[nextIndex] == "1 HIV+ male partner") {
                        totalScore += 4
                        nextIndex += 1
                    } else {
                        nextIndex = -1
                    }

                    if (nextIndex > 0 && results[nextIndex] == "5 or more times") {
                        totalScore += 6
                    }
                }

                taskResult.results.forEach { stepResult ->
                    Log.e("logTag", "answers ${stepResult.results.first()}")
                }
                Log.e("logTag", "totalScore ${totalScore}")

                //taskResult.results.map { res -> (steps.find { step -> step.id==res.results.first().id })?.text  }
            } else {
                surveyView.start(task, configuration)
            }
        }


        surveyView.start(task, configuration)

        return rootView
    }
}