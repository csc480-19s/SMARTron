import React, { Component } from 'react';
import '../css/answerkey.css';
class AnswerKey extends Component{
constructor(){
  super();
  this.state = {
    answerKeys: [],
    list: ["A", "B", "C", "D", "E"],
    chkbox: false,
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
}

  render(){
    return (
      <div align = "right">
        <div className = "scrollable">
          <h1 align = "left">AnswerKey</h1>
          <h1 align = "left">Exam 101</h1>
            <div className = "items">
              {this.state.answerKeys.map( item => (
                <div className = "item">
                  <div key ={item.questionId}>
                    <div className="keyBox">
                      <span>
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
                </div>
              <input type = "submit"
                     onClick={() => this.handleSubmit()}/>
            </div>
          );
        }
}
export default AnswerKey
