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
  fetch(`http://pi.cs.oswego.edu:13126/answerkey?examId=${this.props.location.state.id}&instId=${this.props.location.state.email}`)
  .then(response => response.json())
  .then(result => {
    const keys = result.map(item => {
      return item;
    })
    this.setState({
      answerKeys: keys
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

handleClick(item, index){
  var i = item.answerKey.indexOf(index);
  if(item.answerKey.includes(index)){
    item.answerKey.splice(i,1);
  }else{
    item.answerKey.push(index);
}
}

handleSubmit(e){
  this.props.history.push({pathname:"/home", state:{loginName:this.props.location.state.loginName, email:this.props.location.state.email,exams:this.props.location.state.exams}});
  console.log("handle on submit here")
  console.log(this.state.copyOfAnswerKey);
  this.postAnswerKey();
}

postAnswerKey(){
var data = JSON.stringify(this.state.answerKeys);
console.log(data);
var xhr = new XMLHttpRequest();
xhr.withCredentials = false;

xhr.addEventListener("readystatechange", function () {
  if (this.readyState === 4) {
    console.log(this.responseText);
  }
});

xhr.open("POST", `http://pi.cs.oswego.edu:13126/answerkey?examId=${this.props.location.state.id}`);
xhr.setRequestHeader("Content-Type", "application/json");
xhr.send(data);
}

  render(){
    return (
      <div>
        <div>
        </div>
      <div align = "right">
          <h1 className = "answerKeyTitle" align = "center">Answer Key</h1>
          <h1 className = "examName" align = "center">{this.props.location.state.text}</h1>
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
          <Header history={this.props.history} email={this.props.location.state.email}/>

      </div>

          );
        }
}
export default AnswerKey
