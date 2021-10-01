package nu.oueslati.flagquizz

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat

class FlagQuestions : AppCompatActivity(), View.OnClickListener {
        // val questionsList = Constants.getQuestions()
        // Log.i("Questions size: ","${questionsList.size}")
        private var mCurrentPosition: Int = 1 // Default and the first question position
        private var mQuestionsList: ArrayList<Question>? = null

        private var mSelectedOptionPosition: Int = 0
        private var mCorrectAnswers: Int = 0

        private val optOne = findViewById<TextView>(R.id.tv_option_one)
        private val optTwo = findViewById<TextView>(R.id.tv_option_two)
        private val optThree = findViewById<TextView>(R.id.tv_option_three)
        private val optFour = findViewById<TextView>(R.id.tv_option_four)
        private val btnSubmit = findViewById<Button>(R.id.btn_submit)

        // TODO (STEP 3: Create a variable for getting the name from intent.)
        // START
        private var mUserName: String? = null
        // END

        override fun onCreate(savedInstanceState: Bundle?) {
            //This call the parent constructor
            super.onCreate(savedInstanceState)
            // This is used to align the xml view to this class
            setContentView(R.layout.activity_flag_questions)

            // TODO (STEP 4: Get the NAME from intent and assign it the variable.)
            // START
            mUserName = intent.getStringExtra(Constants.USER_NAME)
            // END

            mQuestionsList = Constants.getQuestions()

            setQuestion()

            optOne.setOnClickListener(this)
            optTwo.setOnClickListener(this)
            optThree.setOnClickListener(this)
            optFour.setOnClickListener(this)
            btnSubmit.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            when (v?.id) {

                R.id.tv_option_one -> {

                    selectedOptionView(optOne, 1)
                }

                R.id.tv_option_two -> {

                    selectedOptionView(optTwo, 2)
                }

                R.id.tv_option_three -> {

                    selectedOptionView(optThree, 3)
                }

                R.id.tv_option_four -> {

                    selectedOptionView(optFour, 4)
                }

                R.id.btn_submit -> {

                    if (mSelectedOptionPosition == 0) {

                        mCurrentPosition++

                        when {

                            mCurrentPosition <= mQuestionsList!!.size -> {

                                setQuestion()
                            }
                            else -> {
                                // START
                                val intent =
                                Intent(this@FlagQuestions, ResultActivity::class.java)
                                intent.putExtra(Constants.USER_NAME, mUserName)
                                intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                                intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionsList!!.size)
                                startActivity(intent)
                                finish()
                                // END
                            }
                        }
                    } else {
                        val question = mQuestionsList?.get(mCurrentPosition - 1)

                        // This is to check if the answer is wrong
                        if (question!!.correctAnswer != mSelectedOptionPosition) {
                            answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                        }
                        else {
                            mCorrectAnswers++
                        }

                        // This is for correct answer
                        answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                        if (mCurrentPosition == mQuestionsList!!.size) {
                            btnSubmit.text = "FINISH"
                        } else {
                            btnSubmit.text = "GO TO NEXT QUESTION"
                        }

                        mSelectedOptionPosition = 0
                    }
                }
            }
        }

        /**
         * A function for setting the question to UI components.
         */
        private fun setQuestion() {

            val question = mQuestionsList!!.get(mCurrentPosition - 1) // Getting the question from the list with the help of current position.
            val progressBar = findViewById<ProgressBar>(R.id.progressBar)
            val tvProgress = findViewById<TextView>(R.id.tv_progress)
            val tvQuestion = findViewById<TextView>(R.id.tv_question)
            val ivImage = findViewById<ImageView>(R.id.iv_image)
            defaultOptionsView()

            if (mCurrentPosition == mQuestionsList!!.size) {
                btnSubmit.text = "FINISH"
            } else {
                btnSubmit.text = "SUBMIT"
            }

            progressBar.progress = mCurrentPosition
            tvProgress.text = "$mCurrentPosition" + "/" + progressBar.getMax()

            tvQuestion.text = question.question
            ivImage.setImageResource(question.image)
            optOne.text = question.optionOne
            optTwo.text = question.optionTwo
            optThree.text = question.optionThree
            optFour.text = question.optionFour
        }

        /**
         * A function to set the view of selected option view.
         */
        private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {

            defaultOptionsView()

            mSelectedOptionPosition = selectedOptionNum

            tv.setTextColor(
                Color.parseColor("#363A43")
            )
            tv.setTypeface(tv.typeface, Typeface.BOLD)
            tv.background = ContextCompat.getDrawable(
                this@FlagQuestions,
                R.drawable.selected_option_border_bg
            )
        }

        /**
         * A function to set default options view when the new question is loaded or when the answer is reselected.
         */
        private fun defaultOptionsView() {

            val options = ArrayList<TextView>()
            options.add(0, optOne)
            options.add(1, optTwo)
            options.add(2, optThree)
            options.add(3, optFour)

            for (option in options) {
                option.setTextColor(Color.parseColor("#7A8089"))
                option.typeface = Typeface.DEFAULT
                option.background = ContextCompat.getDrawable(
                    this@FlagQuestions,
                    R.drawable.default_option_border_bg
                )
            }
        }

        /**
         * A function for answer view which is used to highlight the answer is wrong or right.
         */
        private fun answerView(answer: Int, drawableView: Int) {

            when (answer) {

                1 -> {
                    optOne.background = ContextCompat.getDrawable(
                        this@FlagQuestions,
                        drawableView
                    )
                }
                2 -> {
                    optTwo.background = ContextCompat.getDrawable(
                        this@FlagQuestions,
                        drawableView
                    )
                }
                3 -> {
                    optThree.background = ContextCompat.getDrawable(
                        this@FlagQuestions,
                        drawableView
                    )
                }
                4 -> {
                    optFour.background = ContextCompat.getDrawable(
                        this@FlagQuestions,
                        drawableView
                    )
                }
            }
        }

    }
