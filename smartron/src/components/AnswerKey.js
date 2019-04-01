import React, { Component } from 'react';
import '../css/answerkey.css';
import Header from '../components/Header';
class AnswerKey extends Component{
constructor(){
  super();
  this.state = {
    answerKeys: [],
    keyOptions: ["A", "B", "C", "D", "E"],
    chkbox: false,
    updateAnswerKey: [],
    copyOfAnswerKey: [],
    makeCopy: true,
    examList:[],
    loginName:"",
    email:""
  };
  this.handleClick = this.handleClick.bind(this);
  this.handleSubmit = this.handleSubmit.bind(this);
  this.copyAnswerKey = this.copyAnswerKey.bind(this);
  this.postAnswerKey = this.postAnswerKey.bind(this);
}

componentDidMount(){
  fetch('https://642b4d9c-37ba-432e-823a-30c17de3fb13.mock.pstmn.io/answerKey')
  .then(response => response.json())
  .then(result => {
    const keys = result.map(item => {
      return item;
    })
    this.setState({
      answerKeys: keys,
    })
  });
}

copyAnswerKey(){
  let dupeAnswerKey = JSON.parse(JSON.stringify(this.state.answerKeys));
  this.setState({
    makeCopy:false,
    copyOfAnswerKey:dupeAnswerKey
  });
}

handleClick(item, key){
  if(this.state.makeCopy){
    this.copyAnswerKey();
  }
  var index = item.answerKey.indexOf(key);
    if(item.answerKey.includes(key)){
      item.answerKey.splice(index,1);
    }else{
      item.answerKey.push(key);
    }
    console.log();
  if(this.state.updateAnswerKey.length === 0){
    this.state.updateAnswerKey.push(item);
  }

  let updateList = this.state.updateAnswerKey;
  updateList = updateList.filter(function( obj ){
  return obj.questionId !== item.questionId});

  let anserKeyObj = this.state.copyOfAnswerKey
    .find(obj => obj.questionId === item.questionId);
    item.answerKey.sort();

  if(JSON.stringify(item) !== JSON.stringify(anserKeyObj)){
    updateList.push(item);
  }
  this.setState({
    updateAnswerKey:updateList
  });
}

handleSubmit(e){
  this.props.history.push({pathname:"/home", state:{loginName:this.props.location.state.loginName, email:this.props.location.state.email,exams:this.props.location.state.exams}});
  console.log("handle on submit here")
  console.log(this.state.copyOfAnswerKey);
  console.log(this.state.updateAnswerKey);
  this.postAnswerKey();
}

postAnswerKey(){
  //send answerkey to backend
}
  render(){
    return (
      <div>
        <div>
          <Header />
        </div>
      <div align = "right">
          <h1 className = "answerKeyTitle" align = "center">Answer Key</h1>
          <h1 className = "examName" align = "center">Exam 101</h1>
            <div className = "items">
              {this.state.answerKeys.map( item => (
                <div align = "center" className ={item.answerKey.length !== 0? "item": "itemEmpty"}>
                  <div key ={item.questionId}>
                    <div className="keyBox">
                      <span className="questionId">
                        {item.questionId}
                      </span>
                        {this.state.keyOptions.map(k =>(
                          <span>
                            <input
                              type="checkbox"
                              value={item.questionId}
                              id ={item.questionId+k}
                              name=""
                              defaultChecked={item.answerKey.includes(k) ? true : false}
                              onClick={() => this.handleClick(item, k)}/>
                              <label htmlFor={item.questionId+k}>{k}</label>
                            </span>
                          ))}
                          </div>
                        </div>
                      </div>
                    ))}
                  </div>
              <button className= "float"
                     onClick={() => this.handleSubmit()}>Submit</button>
            </div>
          </div>
          );
        }
}
export default AnswerKey
