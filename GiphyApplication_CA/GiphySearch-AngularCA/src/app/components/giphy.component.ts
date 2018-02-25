import { Component, OnInit, Input, ViewChild } from '@angular/core';
import {GiphyModel} from '../giphyModel';
import {NgForm} from '@angular/forms';
import {GiphyService} from '../giphyService';



@Component({
  selector: 'app-giphy',
  templateUrl: './giphy.component.html',
  styleUrls: ['./giphy.component.css']
})
export class GiphyComponent implements OnInit {

  @Input()
  model: GiphyModel;

  @Input()
  userid: string;

  constructor(private giphyService: GiphyService) { }

  ngOnInit() {}

  addToLikeList (form: NgForm) {

    console.log(form.value.userid);
    let m: GiphyModel = {
      userid: this.userid,
      url: this.model.url,
      title: this.model.title,
    };

    
    //console.log(m);

    this.giphyService.addToLikeList(m).then(data => console.log(data));
  }
}
