import React, { Component } from 'react';
import '../css/answerkey.css';
import Header from '../components/Header';
class AnswerKey extends Component{
constructor(){
  super();
  this.state = {
    answerKeys: [],
    list: ["A", "B", "C", "D", "E"],
    chkbox: false,
      examList:[],
      loginName:"",
      email:"",
  };
  this.handleClick = this.handleClick.bind(this);
  this.handleSubmit = this.handleSubmit.bind(this);
}

componentDidMount(){
  fetch('./answerkey.json')
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

handleClick(item, index){
  var i = item.answerKey.indexOf(index);
  if(item.answerKey.includes(index)){
    item.answerKey.splice(i,1);
  }else{
    item.answerKey.push(index);
  }
}

handleSubmit(e){
  console.log("handle on submit here")
   // this.props.history.push({pathname:"/home", state:{loginName:this.props.location.state.loginName, email:this.props.location.state.email,examList:this.props.location.state.examList}});
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
                <div className = "item">
                  <div key ={item.questionId}>
                    <div className="keyBox">
                      <span className="questionId">
                        {item.questionId}
                      </span>
                        {this.state.list.map(k =>(
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
